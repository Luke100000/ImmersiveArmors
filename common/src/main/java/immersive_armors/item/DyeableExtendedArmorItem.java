package immersive_armors.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableItem;

public class DyeableExtendedArmorItem extends ExtendedArmorItem implements DyeableItem {
    public DyeableExtendedArmorItem(Settings settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);
    }
}
