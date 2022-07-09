package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "getLevel", at = @At("RETURN"), cancellable = true)
    private static void getLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Math.max(cir.getReturnValue(), getStaticLevel(enchantment, stack)));
    }
}
