package immersive_armors.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;

public class DyeableExtendedArmorItem extends ExtendedArmorItem {
    public DyeableExtendedArmorItem(Properties settings, ArmorItem.Type slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);

        CauldronInteraction.WATER.map().put(this, CauldronInteraction.DYED_ITEM);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        tooltip.add(Component.translatable("immersive_armors.dyeable").withStyle(ChatFormatting.GOLD));
    }

    public int getColor(ItemStack stack) {
        DyedItemColor dyedColorComponent = stack.get(DataComponents.DYED_COLOR);
        return (dyedColorComponent != null ? dyedColorComponent.rgb() : getExtendedMaterial().getColor()) | 0xFF000000;
    }
}
