package immersive_armors.item;

import immersive_armors.util.FlowingText;
import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TooltipItem extends Item {
    public TooltipItem(Item.Settings properties) {
        super(properties);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.addAll(FlowingText.wrap(new TranslatableText(getTranslationKey(stack) + ".tooltip").formatted(Formatting.GRAY), 160));
    }
}
