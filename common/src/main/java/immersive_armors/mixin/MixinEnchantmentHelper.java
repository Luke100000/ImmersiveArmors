package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentHelper.class)
public abstract class MixinEnchantmentHelper {
    private static int getStaticLevel(Enchantment enchantment, ItemStack stack) {
        if (stack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem item = (ExtendedArmorItem)stack.getItem();

            if (item.getMaterial().hasEnchantment(enchantment)) {
                return item.getMaterial().getEnchantment(enchantment);
            }
        }
        return 0;
    }

    private static int getLevelVanilla(Enchantment enchantment, ItemStack stack) {
        if (!stack.isEmpty()) {
            Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
            NbtList nbtList = stack.getEnchantments();

            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                Identifier identifier2 = Identifier.tryParse(nbtCompound.getString("id"));
                if (identifier2 != null && identifier2.equals(identifier)) {
                    return MathHelper.clamp(nbtCompound.getInt("lvl"), 0, 255);
                }
            }

        }
        return 0;
    }

    /**
     * @author Luke100000
     * Overwrite required since getLevel is public static
     */
    @Overwrite
    public static int getLevel(Enchantment enchantment, ItemStack stack) {
        return Math.max(getLevelVanilla(enchantment, stack), getStaticLevel(enchantment, stack));
    }
}
