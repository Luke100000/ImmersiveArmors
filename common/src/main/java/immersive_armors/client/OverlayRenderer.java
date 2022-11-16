package immersive_armors.client;

import immersive_armors.Config;
import immersive_armors.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OverlayRenderer {
    private static final ItemStack clock = new ItemStack(Items.CLOCK);
    private static final ItemStack compass = new ItemStack(Items.COMPASS);

    public static void renderOverlay() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.options.hudHidden && client.interactionManager != null) {
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

    private static void renderSteampunkHud() {
        MinecraftClient client = MinecraftClient.getInstance();
        // Offset item when offhand slot is rendered
        Arm arm = null;
        PlayerEntity playerEntity = !(client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)client.getCameraEntity();
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffHandStack();
            if (!itemStack.isEmpty()) {
                arm = playerEntity.getMainArm().getOpposite();
            }
        }

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();
        client.getItemRenderer().renderInGuiWithOverrides(clock, scaledWidth / 2 + (arm == Arm.LEFT ? Config.getInstance().hudClockXOffhand : Config.getInstance().hudClockX), scaledHeight + Config.getInstance().hudClockY);
        client.getItemRenderer().renderInGuiWithOverrides(compass, scaledWidth / 2 + (arm == Arm.RIGHT ? Config.getInstance().hudCompassXOffhand : Config.getInstance().hudCompassX), scaledHeight + Config.getInstance().hudCompassY);
    }
}
