package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemGroups {
    public static final ItemGroup ARMOR = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
            () -> Items.items.stream().filter(i -> Registry.ITEM.getId(i).getPath().equals("divine_chestplate")).findAny().orElse(Items.items.get(0)).getDefaultStack()
    );
}
