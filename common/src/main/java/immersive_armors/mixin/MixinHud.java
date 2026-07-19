package immersive_armors.mixin;

import immersive_armors.client.OverlayRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class MixinHud {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void immersiveArmors$renderInject(GuiGraphicsExtractor context, DeltaTracker tickCounter, CallbackInfo ci) {
        OverlayRenderer.renderOverlay(context);
    }
}
