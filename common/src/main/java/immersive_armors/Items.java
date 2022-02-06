package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import immersive_armors.item.TestItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Items {
    Item TEST_ITEM = register("test", new TestItem(baseProps()));

    net.minecraft.item.ItemGroup ItemGroup = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
            Items.TEST_ITEM::getDefaultStack
    );
    ;

    static void bootstrap() {
        Tags.Blocks.bootstrap();
    }

    static Item register(String name, Item item) {
        return Registration.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    static Item.Settings baseProps() {
        return new Item.Settings().group(ItemGroup);
    }
}
