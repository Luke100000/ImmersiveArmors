package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ci.cancel();
        }
    }

    private void renderPieces(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, float tickDelta) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem armorItem = (ExtendedArmorItem)itemStack.getItem();
            if (armorItem.getSlotType() == armorSlot) {
                armorItem.getMaterial().getPieces(armorSlot).forEach(piece -> {
                    //noinspection unchecked
                    piece.render(matrices, vertexConsumers, light, entity, itemStack, tickDelta, armorSlot, (BipedEntityModel<LivingEntity>)getContextModel());
                });
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float tickDelta, float j, float k, float l, CallbackInfo ci) {
        // features
        renderPieces(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.HEAD, i, tickDelta);
        renderPieces(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, tickDelta);
        renderPieces(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.LEGS, i, tickDelta);
        renderPieces(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.FEET, i, tickDelta);
    }
}
