package immersive_armors.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnchantmentHelper.class)
public abstract class MixinEnchantmentHelper {
    private static int getEnchantmentLevel(Enchantment enchantment, ItemStack stack) {
        if (stack.getItem() instanceof ExtendedArmorItem item) {
            if (item.getMaterial().hasEnchantment(enchantment)) {
                return item.getMaterial().getEnchantment(enchantment);
            }
        }
        return 0;
    }

    private static int level;

    @Inject(method = "getLevel", at = @At("HEAD"))
    private static void immersiveArmors$getLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        level = getEnchantmentLevel(enchantment, stack);
    }

    @SuppressWarnings("unused")
    @ModifyReturnValue(method = "getLevel", at = @At("RETURN"))
    private static int immersiveArmors$modifyGetLevel(int oldLevel) {
        return Math.max(oldLevel, level);
    }
}
