package immersive_armors.item;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class DyeableExtendedArmorItem extends ExtendedArmorItem {
    public DyeableExtendedArmorItem(Settings settings, ArmorItem.Type slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);

        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(this, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        tooltip.add(Text.translatable("immersive_armors.dyeable").formatted(Formatting.GOLD));
    }

    public int getColor(ItemStack stack) {
        DyedColorComponent dyedColorComponent = stack.get(DataComponentTypes.DYED_COLOR);
        return (dyedColorComponent != null ? dyedColorComponent.rgb() : getExtendedMaterial().getColor()) | 0xFF000000;
    }
}
