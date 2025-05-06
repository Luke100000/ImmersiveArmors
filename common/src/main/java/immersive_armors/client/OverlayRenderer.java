package immersive_armors.client;

import immersive_armors.Main;
import immersive_armors.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class OverlayRenderer {
    private static final ItemStack clock = new ItemStack(Items.CLOCK);
    private static final ItemStack compass = new ItemStack(Items.COMPASS);

    public static void renderOverlay(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();
        if (!client.options.hideGui && client.gameMode != null && client.player != null) {
            for (ItemStack item : client.player.getArmorSlots()) {
                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.getItem());
                if (id.equals(Main.locate("steampunk_chestplate"))) {
                    renderSteampunkHud(context);
                }
            }
        }
    }

    private static void renderSteampunkHud(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();

        // Offset item when offhand slot is rendered
        HumanoidArm arm = null;
        Player playerEntity = (client.getCameraEntity() instanceof Player player) ? player : null;
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffhandItem();
            if (!itemStack.isEmpty()) {
                arm = playerEntity.getMainArm().getOpposite();
            }
        }

        int scaledWidth = client.getWindow().getGuiScaledWidth();
        int scaledHeight = client.getWindow().getGuiScaledHeight();

        context.renderItem(clock, scaledWidth / 2 + (arm == HumanoidArm.LEFT ? Config.getInstance().hudClockXOffhand : Config.getInstance().hudClockX), scaledHeight + Config.getInstance().hudClockY);
        context.renderItem(compass, scaledWidth / 2 + (arm == HumanoidArm.RIGHT ? Config.getInstance().hudCompassXOffhand : Config.getInstance().hudCompassX), scaledHeight + Config.getInstance().hudCompassY);
    }
}
