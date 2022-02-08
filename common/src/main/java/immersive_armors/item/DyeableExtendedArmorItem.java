package immersive_armors.item;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DyeableExtendedArmorItem extends ExtendedArmorItem implements DyeableItem {
    public DyeableExtendedArmorItem(Settings settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("immersive_armors.dyeable").formatted(Formatting.GOLD));
    }
}
