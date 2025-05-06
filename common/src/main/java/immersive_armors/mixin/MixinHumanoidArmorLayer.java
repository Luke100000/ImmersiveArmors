package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.Main;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = HumanoidArmorLayer.class, priority = 700)
public abstract class MixinHumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public MixinHumanoidArmorLayer(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$injectRenderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        // Prevent the armor from being rendered
        if (Main.FORGE) {
            ItemStack itemStack = entity.getItemBySlot(armorSlot);
            if (itemStack.getItem() instanceof ExtendedArmorItem) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void immersiveArmors$render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T entity, float f, float g, float tickDelta, float j, float k, float l, CallbackInfo ci) {
        // Forge removed renderArmorParts calls
        if (Main.FORGE) {
            immersiveArmors$renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.HEAD);
            immersiveArmors$renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.CHEST);
            immersiveArmors$renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.LEGS);
            immersiveArmors$renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.FEET);
        }
    }

    @Unique
    private void immersiveArmors$renderPieces(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float tickDelta, EquipmentSlot armorSlot) {
        if (entity != null) {
            ItemStack equippedStack = entity.getItemBySlot(armorSlot);
            if (equippedStack.getItem() instanceof ExtendedArmorItem item) {
                item.getExtendedMaterial().getPieces(item.getEquipmentSlot()).forEach(piece -> piece.render(matrices, vertexConsumers, light, entity, equippedStack, tickDelta, item.getEquipmentSlot(), this.getParentModel()));
            }
        }
    }
}
