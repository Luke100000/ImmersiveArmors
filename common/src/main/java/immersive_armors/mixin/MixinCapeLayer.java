package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class MixinCapeLayer {
    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$render(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, AvatarRenderState renderState, float f, float g, CallbackInfo ci) {
        if (renderState.showCape && !renderState.isInvisible && renderState.skin.cape() != null) {
            ItemStack itemStack = renderState.chestEquipment;
            if (itemStack.getItem() instanceof ExtendedArmorItem armorItem && armorItem.getExtendedMaterial().shouldHideCape()) {
                ci.cancel();
            }
        }
    }
}
