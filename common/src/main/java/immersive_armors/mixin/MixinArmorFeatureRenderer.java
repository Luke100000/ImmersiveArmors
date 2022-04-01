package immersive_armors.mixin;

import immersive_armors.client.render.entity.model.*;
import immersive_armors.client.render.entity.piece.Piece;
import immersive_armors.item.ArmorPiece;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    private final HashMap<Supplier<EntityModel>, EntityModel> models = new HashMap<>();
    private final HashMap<Supplier<Piece>, Piece> renderers = new HashMap<>();

    @Accessor("ARMOR_TEXTURE_CACHE")
    abstract Map<String, Identifier> getArmorCache();

    @Shadow @Final private A leggingsModel;
    @Shadow @Final private A bodyModel;

    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    private boolean usesSecondLayer(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private A getArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? leggingsModel : bodyModel;
    }

    private Identifier getExtendedArmorTexture(ExtendedArmorItem item, boolean overlay, ArmorPiece piece) {
        String string = "immersive_armors:textures/models/armor/" + item.getMaterial().getName() + "/" + piece.getTexture() + (overlay ? "_overlay": "") + ".png";
        return getArmorCache().computeIfAbsent(string, Identifier::new);
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ExtendedArmorItem item, EntityModel model, float red, float green, float blue, boolean overlay, ArmorPiece piece) {
        RenderLayer renderLayer;
        if (piece.isTranslucent()) {
            renderLayer = RenderLayer.getEntityTranslucent(getExtendedArmorTexture(item, overlay, piece));
        } else if (piece.isGlowing()) {
            renderLayer = RenderLayer.getBeaconBeam(getExtendedArmorTexture(item, overlay, piece), false);
        } else {
            renderLayer = RenderLayer.getArmorCutoutNoCull(getExtendedArmorTexture(item, overlay, piece));
        }
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, renderLayer, false, piece.isGlint());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ci.cancel();
        }
    }

    @Shadow
    protected abstract void setVisible(A bipedModel, EquipmentSlot slot);

    private void renderExtendedArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack itemStack, EquipmentSlot armorSlot, EntityModel model, ArmorPiece piece) {
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem armorItem = (ExtendedArmorItem)itemStack.getItem();
            if (model instanceof BipedEntityModel) {
                @SuppressWarnings("unchecked") A bipedEntityModel = (A)model;
                this.getContextModel().setAttributes(bipedEntityModel);
                this.setVisible(bipedEntityModel, armorSlot);
            } else if (model instanceof DecoModel) {
                A bipedEntityModel = getArmor(armorSlot);
                this.getContextModel().setAttributes(bipedEntityModel);
                this.setVisible(bipedEntityModel, armorSlot);
                ((DecoModel)model).copyFromModel(bipedEntityModel);
            }

            if (piece.isColored()) {
                int i = ((DyeableItem)armorItem).getColor(itemStack);
                float red = (float)(i >> 16 & 255) / 255.0F;
                float green = (float)(i >> 8 & 255) / 255.0F;
                float blue = (float)(i & 255) / 255.0F;
                renderArmorParts(matrices, vertexConsumers, light, armorItem, model, red, green, blue, false, piece);
                renderArmorParts(matrices, vertexConsumers, light, armorItem, model, 1.0F, 1.0F, 1.0F, true, piece);
            } else {
                renderArmorParts(matrices, vertexConsumers, light, armorItem, model, 1.0F, 1.0F, 1.0F, false, piece);
            }
        }
    }

    private void renderDecoModels(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, float tickDelta) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem armorItem = (ExtendedArmorItem)itemStack.getItem();
            if (armorItem.getSlotType() == armorSlot) {
                armorItem.getMaterial().getPieces().forEach(piece -> {
                    if (piece.getEquipmentSlots().contains(armorSlot)) {
                        if (piece.getModel() != null) {
                            EntityModel decoModel = models.computeIfAbsent(piece.getModel(), Supplier::get);
                            renderExtendedArmor(matrices, vertexConsumers, light, itemStack, armorSlot, decoModel, piece);
                        }
                        if (piece.getRenderer() != null) {
                            Piece renderer = renderers.computeIfAbsent(piece.getRenderer(), Supplier::get);
                            renderer.render(matrices, vertexConsumers, light, entity, itemStack, tickDelta, piece);
                        }
                    }
                });
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float tickDelta, float j, float k, float l, CallbackInfo ci) {
        // features
        renderDecoModels(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, tickDelta);
        renderDecoModels(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, tickDelta);
        renderDecoModels(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, tickDelta);
        renderDecoModels(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, tickDelta);
    }
}
