package immersive_armors.util;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DamageUtils {
    private static float apply(LivingEntity entity, EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = entity.getItemBySlot(slot);

        //noinspection ConstantConditions
        if (stack != null && stack.getItem() instanceof ExtendedArmorItem armor) {
            amount = armor.applyArmorToDamage(entity, source, amount, stack);
        }

        return amount;
    }

    private static float applyToAttacker(LivingEntity entity, LivingEntity attacker, EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = attacker.getItemBySlot(slot);

        //noinspection ConstantConditions
        if (stack != null && stack.getItem() instanceof ExtendedArmorItem armor) {
            amount = armor.applyArmorToAttack(entity, source, amount, stack);
        }

        return amount;
    }

    public static float adjustDamage(LivingEntity entity, DamageSource source, float amount) {
        amount = apply(entity, EquipmentSlot.HEAD, source, amount);
        amount = apply(entity, EquipmentSlot.CHEST, source, amount);
        amount = apply(entity, EquipmentSlot.LEGS, source, amount);
        amount = apply(entity, EquipmentSlot.FEET, source, amount);

        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            amount = applyToAttacker(entity, livingAttacker, EquipmentSlot.HEAD, source, amount);
            amount = applyToAttacker(entity, livingAttacker, EquipmentSlot.CHEST, source, amount);
            amount = applyToAttacker(entity, livingAttacker, EquipmentSlot.LEGS, source, amount);
            amount = applyToAttacker(entity, livingAttacker, EquipmentSlot.FEET, source, amount);
        }

        return amount;
    }
}
