package immersive_armors.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyeableExtendedArmorItem extends ExtendedArmorItem implements DyeableItem {
    public DyeableExtendedArmorItem(Settings settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
        super(settings, slot, material);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("immersive_armors.dyeable").formatted(Formatting.GOLD));
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubTag("display");
        return nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : getMaterial().getColor();
    }
}
