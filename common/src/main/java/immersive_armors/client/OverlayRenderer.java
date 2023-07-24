package immersive_armors.client;

import immersive_armors.Main;
import immersive_armors.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class OverlayRenderer {
    private static final ItemStack clock = new ItemStack(Items.CLOCK);
    private static final ItemStack compass = new ItemStack(Items.COMPASS);

    public static void renderOverlay(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.options.hudHidden && client.interactionManager != null && client.player != null) {
            for (ItemStack item : client.player.getArmorItems()) {
                Identifier id = Registries.ITEM.getId(item.getItem());
                if (id.equals(Main.locate("steampunk_chestplate"))) {
                    renderSteampunkHud(context);
                }
            }
        }
    }

    private static void renderSteampunkHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Offset item when offhand slot is rendered
        Arm arm = null;
        PlayerEntity playerEntity = (client.getCameraEntity() instanceof PlayerEntity player) ? player : null;
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffHandStack();
            if (!itemStack.isEmpty()) {
                arm = playerEntity.getMainArm().getOpposite();
            }
        }

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();

        context.drawItem(clock, scaledWidth / 2 + (arm == Arm.LEFT ? Config.getInstance().hudClockXOffhand : Config.getInstance().hudClockX), scaledHeight + Config.getInstance().hudClockY);
        context.drawItem(compass, scaledWidth / 2 + (arm == Arm.RIGHT ? Config.getInstance().hudCompassXOffhand : Config.getInstance().hudCompassX), scaledHeight + Config.getInstance().hudCompassY);
    }
}
