package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot arg);

    private float apply(EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = this.getEquippedStack(slot);

        if (stack != null) {
            if (stack.getItem() instanceof ExtendedArmorItem) {
                ExtendedArmorItem armor = (ExtendedArmorItem)stack.getItem();
                amount = armor.applyArmorToDamage(source, amount, stack);
            }
        }
        return amount;
    }

    @Inject(method = "applyArmorToDamage", at = @At("RETURN"), cancellable = true)
    void applyArmorToDamage(DamageSource source, float unused, CallbackInfoReturnable<Float> cir) {
        float amount = cir.getReturnValue();

        amount = apply(EquipmentSlot.HEAD, source, amount);
        amount = apply(EquipmentSlot.CHEST, source, amount);
        amount = apply(EquipmentSlot.LEGS, source, amount);
        amount = apply(EquipmentSlot.FEET, source, amount);

        cir.setReturnValue(amount);
    }
}
