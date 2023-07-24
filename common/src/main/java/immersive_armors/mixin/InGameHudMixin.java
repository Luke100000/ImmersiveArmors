package immersive_armors.mixin;

import immersive_armors.client.OverlayRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderHotbar(FLnet/minecraft/client/gui/DrawContext;)V", at = @At("TAIL"))
    private void immersiveArmors$renderInject(float tickDelta, DrawContext context, CallbackInfo ci) {
        OverlayRenderer.renderOverlay(context);
    }
}
