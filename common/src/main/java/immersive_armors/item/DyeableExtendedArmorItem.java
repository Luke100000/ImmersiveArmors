package immersive_armors.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.ArmorType;

public class DyeableExtendedArmorItem extends ExtendedArmorItem {
    public DyeableExtendedArmorItem(Properties settings, ArmorType slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);

        tooltip.accept(Component.translatable("immersive_armors.dyeable").withStyle(ChatFormatting.GOLD));
    }

    public int getColor(ItemStack stack) {
        DyedItemColor dyedColorComponent = stack.get(DataComponents.DYED_COLOR);
        return (dyedColorComponent != null ? dyedColorComponent.rgb() : getExtendedMaterial().getColor()) | 0xFF000000;
    }
}
