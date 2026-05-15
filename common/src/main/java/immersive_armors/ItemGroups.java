package immersive_armors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ItemGroups {
    private static boolean itemsReady;

    public static void markItemsReady() {
        itemsReady = true;
    }

    public static Identifier getIdentifier() {
        return Main.locate(Main.MOD_ID + "_tab");
    }

    public static Component getDisplayName() {
        return Component.translatable("itemGroup." + ItemGroups.getIdentifier().toLanguageKey());
    }

    public static ItemStack getIcon() {
        if (!itemsReady) {
            return new ItemStack(net.minecraft.world.item.Items.IRON_HELMET);
        }
        return Items.items.getOrDefault("divine_chestplate", () -> net.minecraft.world.item.Items.IRON_HELMET).get().getDefaultInstance();
    }
}
