package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ItemGroups {
   public static ItemGroup ARMOR = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
           () -> Items.DIVINE_ARMOR[1].getDefaultStack()
    );
}
