package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import immersive_armors.item.ArmorLayer;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Items {
    ExtendedArmorMaterial emerald = new ExtendedArmorMaterial("emerald")
            .protectionAmount(2, 3, 2, 1)
            .toughness(1.0f)
            .layer(ArmorLayer.LOWER)
            .colored(ArmorLayer.LOWER);

    Item EMERALD_CHESTPLATE = register("emerald_chestplate", new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, emerald));

    ItemGroup itemGroup = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
            Items.EMERALD_CHESTPLATE::getDefaultStack
    );

    static void bootstrap() {
        Tags.Blocks.bootstrap();
        Tags.Items.bootstrap();
    }

    static Item register(String name, Item item) {
        return Registration.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    static Item.Settings baseProps() {
        return new Item.Settings().group(itemGroup);
    }
}
