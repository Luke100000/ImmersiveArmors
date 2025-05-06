package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class MixinCapeLayer {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, AbstractClientPlayer player, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (player.isModelPartShown(PlayerModelPart.CAPE) && !player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE) && player.getSkin().capeTexture() != null) {
            ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (itemStack.getItem() instanceof ExtendedArmorItem && ((ExtendedArmorItem) itemStack.getItem()).getExtendedMaterial().shouldHideCape()) {
                ci.cancel();
            }
        }
    }
}
