package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup ARMOR = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
            () -> Items.items.getOrDefault("divine_chestplate", () -> net.minecraft.item.Items.IRON_HELMET).get().getDefaultStack()
    );
}
