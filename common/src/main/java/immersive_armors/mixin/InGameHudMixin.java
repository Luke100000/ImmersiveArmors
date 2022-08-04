package immersive_armors.mixin;

import immersive_armors.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final

    private MinecraftClient client;
    @Shadow
    private int scaledHeight, scaledWidth;

    private static final ItemStack clock = new ItemStack(Items.CLOCK);
    private static final ItemStack compass = new ItemStack(Items.COMPASS);

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At("TAIL"))
    private void renderInject(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!this.client.options.hudHidden && this.client.interactionManager != null && this.client.interactionManager.hasStatusBars()) {
            if (client.player != null) {
                for (ItemStack item : client.player.getArmorItems()) {
                    Identifier id = Registry.ITEM.getId(item.getItem());
                    if (id.equals(Main.locate("steampunk_chestplate"))) {
                        renderSteampunkHud();
                    }
                }
            }
        }
    }

    private void renderSteampunkHud() {
        client.getItemRenderer().renderInGuiWithOverrides(clock, scaledWidth / 2 - 91 - 20, scaledHeight - 20);
        client.getItemRenderer().renderInGuiWithOverrides(compass, scaledWidth / 2 + 91 + 4, scaledHeight - 20);
    }
}
