package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import immersive_armors.item.ArmorLayer;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Items {
    ExtendedArmorMaterial emerald = new ExtendedArmorMaterial("emerald")
            .protectionAmount(2, 3, 2, 1)
            .toughness(1.0f)
            .layer(ArmorLayer.LOWER)
            .colored(ArmorLayer.LOWER);

    Item EMERALD_CHESTPLATE = register("emerald_chestplate", new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, emerald));

    Item[] BONE_ARMOR = registerSet(new ExtendedArmorMaterial("bone")
            .protectionAmount(2, 3, 1, 1)
            .enchantability(20)
            .equipSound(SoundEvents.ENTITY_SKELETON_AMBIENT)
            .weight(-0.01f));

    ItemGroup itemGroup = Registration.ObjectBuilders.ItemGroups.create(
            new Identifier(Main.MOD_ID, Main.MOD_ID + "_tab"),
            Items.EMERALD_CHESTPLATE::getDefaultStack
    );

    static void bootstrap() {
        Tags.Blocks.bootstrap();
        Tags.Items.bootstrap();
    }

    static Item[] registerSet(ExtendedArmorMaterial material) {
        return new Item[] {
                register(material.getName() + "_helmet", new ExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material)),
                register(material.getName() + "_chestplate", new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material)),
                register(material.getName() + "_leggings", new ExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material)),
                register(material.getName() + "_boots", new ExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material)),
        };
    }

    static Item register(String name, Item item) {
        return Registration.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    static Item.Settings baseProps() {
        return new Item.Settings().group(itemGroup);
    }
}
