package immersive_armors.mixin;

import immersive_armors.client.render.entity.feature.ExtendedCapeFeatureRenderer;
import immersive_armors.client.render.entity.model.*;
import immersive_armors.item.ArmorLayer;
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
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;
import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private A leggingsModelLower;
    private A bodyModelLower;

    private A leggingsModelUpper;
    private A bodyModelUpper;

    private DecoModel headHorizontal;
    private DecoModel headVertical;
    private DecoModel prismarine;
    private DecoModel shoulder;

    private ExtendedCapeFeatureRenderer<T, CapeModel<T>> capeFeature;

    @Accessor("ARMOR_TEXTURE_CACHE")
    abstract Map<String, Identifier> getArmorCache();

    @Accessor
    abstract A getLeggingsModel();

    @Accessor
    abstract A getBodyModel();

    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/feature/FeatureRendererContext;Lnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/client/render/entity/model/BipedEntityModel;)V", at = @At("TAIL"))
    public void ExtendedArmorFeatureRenderer(FeatureRendererContext context, BipedEntityModel leggingsModel, BipedEntityModel bodyModel, CallbackInfo ci) {
        leggingsModelLower = (A)new BipedEntityModel<T>(0.1f);
        bodyModelLower = (A)new BipedEntityModel<T>(0.6f);

        leggingsModelUpper = (A)new BipedEntityModel<T>(1.0f);
        bodyModelUpper = (A)new BipedEntityModel<T>(1.5f);

        MixinAnimalModel animalModel = (MixinAnimalModel)bodyModel;

        headHorizontal = new HorizontalHeadModel(animalModel);
        headVertical = new VerticalHeadModel(animalModel);
        prismarine = new PrismarineModel(animalModel);
        shoulder = new ShoulderModel(animalModel);

        capeFeature = new ExtendedCapeFeatureRenderer(context, new CapeModel<>());
    }

    private boolean usesSecondLayer(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private A getArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? getLeggingsModel() : getBodyModel();
    }

    private A getLowerArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? leggingsModelLower : bodyModelLower;
    }

    private A getUpperArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? leggingsModelUpper : bodyModelUpper;
    }

    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    void getArmorTexture(ArmorItem item, boolean legs, String overlay, CallbackInfoReturnable<Identifier> cir) {
        if (item instanceof ExtendedArmorItem) {
            cir.setReturnValue(getExtendedArmorTexture(item, legs, overlay, ArmorLayer.MIDDLE));
        }
    }

    private Identifier getExtendedArmorTexture(ArmorItem item, boolean legs, @Nullable String overlay, ArmorLayer armorLayer) {
        String string;
        if (item instanceof ExtendedArmorItem) {
            string = "immersive_armors:textures/models/armor/" + item.getMaterial().getName() + "/layer_" + (legs ? 2 : 1) + "_" + armorLayer.name().toLowerCase(Locale.ENGLISH) + (overlay == null ? "" : "_" + overlay) + ".png";
        } else {
            string = "textures/models/armor/" + item.getMaterial().getName() + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
        }

        Identifier identifier = new Identifier(string);

        // some support mods with custom models
        String vanilla = "minecraft:textures/models/armor/" + item.getMaterial().getName() + "_layer_" + (legs ? 2 : 1) + ".png";
        getArmorCache().put(vanilla, identifier);

        return getArmorCache().computeIfAbsent(string, s -> identifier);
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, EntityModel model, boolean legs, float red, float green, float blue, @Nullable String overlay, ArmorLayer armorLayer) {
        RenderLayer renderLayer;
        if (item instanceof ExtendedArmorItem && ((ExtendedArmorItem)item).getMaterial().isTranslucent(armorLayer)) {
            renderLayer = RenderLayer.getEntityTranslucent(getExtendedArmorTexture(item, legs, overlay, armorLayer));
        } else if (item instanceof ExtendedArmorItem && ((ExtendedArmorItem)item).getMaterial().isGlowing(armorLayer)) {
            renderLayer = RenderLayer.getBeaconBeam(getExtendedArmorTexture(item, legs, overlay, armorLayer), false);
        } else {
            renderLayer = RenderLayer.getArmorCutoutNoCull(getExtendedArmorTexture(item, legs, overlay, armorLayer));
        }
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, renderLayer, false, glint);
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

    private void renderExtendedArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, EntityModel model, ArmorLayer armorLayer) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ArmorItem armorItem = (ArmorItem)itemStack.getItem();
            if (armorItem.getSlotType() == armorSlot && hasLayer(armorItem, armorLayer)) {
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
                boolean secondLayer = this.usesSecondLayer(armorSlot);
                boolean hasGlint = itemStack.hasGlint() || this.hasGlint(armorItem, armorLayer);
                if (isColored(armorItem, armorLayer)) {
                    int i = ((DyeableItem)armorItem).getColor(itemStack);
                    float red = (float)(i >> 16 & 255) / 255.0F;
                    float green = (float)(i >> 8 & 255) / 255.0F;
                    float blue = (float)(i & 255) / 255.0F;
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, red, green, blue, null, armorLayer);
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, 1.0F, 1.0F, 1.0F, "overlay", armorLayer);
                } else {
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, 1.0F, 1.0F, 1.0F, null, armorLayer);
                }
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        // middle layer
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, getArmor(EquipmentSlot.CHEST), ArmorLayer.MIDDLE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, getArmor(EquipmentSlot.LEGS), ArmorLayer.MIDDLE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, getArmor(EquipmentSlot.FEET), ArmorLayer.MIDDLE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, getArmor(EquipmentSlot.HEAD), ArmorLayer.MIDDLE);

        // lower layer
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, getLowerArmor(EquipmentSlot.CHEST), ArmorLayer.LOWER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, getLowerArmor(EquipmentSlot.LEGS), ArmorLayer.LOWER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, getLowerArmor(EquipmentSlot.FEET), ArmorLayer.LOWER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, getLowerArmor(EquipmentSlot.HEAD), ArmorLayer.LOWER);

        // upper layer
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, getUpperArmor(EquipmentSlot.CHEST), ArmorLayer.UPPER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, getUpperArmor(EquipmentSlot.LEGS), ArmorLayer.UPPER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, getUpperArmor(EquipmentSlot.FEET), ArmorLayer.UPPER);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, getUpperArmor(EquipmentSlot.HEAD), ArmorLayer.UPPER);

        // special layers
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, headHorizontal, ArmorLayer.HEAD_HORIZONTAL);

        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, headVertical, ArmorLayer.HEAD_VERTICAL);

        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, prismarine, ArmorLayer.PRISMARINE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, prismarine, ArmorLayer.PRISMARINE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, prismarine, ArmorLayer.PRISMARINE);
        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, prismarine, ArmorLayer.PRISMARINE);

        renderExtendedArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, shoulder, ArmorLayer.SHOULDER);

        // capes
        capeFeature.render(matrixStack, vertexConsumerProvider, i, entity, f, g, h, j, k, l);
    }

    private boolean hasLayer(ArmorItem item, ArmorLayer layer) {
        if (item instanceof ExtendedArmorItem) {
            return ((ExtendedArmorItem)item).getMaterial().hasLayer(layer);
        } else {
            return layer == ArmorLayer.MIDDLE;
        }
    }

    private boolean isColored(ArmorItem item, ArmorLayer layer) {
        if (item instanceof ExtendedArmorItem) {
            return ((ExtendedArmorItem)item).getMaterial().isColored(layer);
        } else {
            return layer == ArmorLayer.MIDDLE && item instanceof DyeableArmorItem;
        }
    }

    private boolean hasGlint(ArmorItem item, ArmorLayer layer) {
        if (item instanceof ExtendedArmorItem) {
            return ((ExtendedArmorItem)item).getMaterial().hasGlint(layer);
        } else {
            return false;
        }
    }
}
