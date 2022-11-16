package immersive_armors.mixin;

import immersive_armors.Config;
import immersive_armors.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
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
        if (!this.client.options.hudHidden && this.client.interactionManager != null) {
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

    private PlayerEntity getCameraPlayer() {
        return !(this.client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)this.client.getCameraEntity();
    }

    private void renderSteampunkHud() {
        // Offset item when offhand slot is rendered
        Arm arm = null;
        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffHandStack();
            if (!itemStack.isEmpty()) {
                arm = playerEntity.getMainArm().getOpposite();
            }
        }

        client.getItemRenderer().renderInGuiWithOverrides(clock, scaledWidth / 2 + (arm == Arm.LEFT ? Config.getInstance().hudClockXOffhand : Config.getInstance().hudClockX), scaledHeight + Config.getInstance().hudClockY);
        client.getItemRenderer().renderInGuiWithOverrides(compass, scaledWidth / 2 + (arm == Arm.RIGHT ? Config.getInstance().hudCompassXOffhand : Config.getInstance().hudCompassX), scaledHeight + Config.getInstance().hudCompassY);
    }
}
