package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ItemGroups {
   @SuppressWarnings("Convert2MethodRef")
   public static ItemGroup ARMOR = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
           () -> Items.EMERALD_CHESTPLATE.getDefaultStack()
    );
}
