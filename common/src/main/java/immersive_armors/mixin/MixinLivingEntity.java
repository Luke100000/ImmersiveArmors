package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    protected MixinLivingEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot arg);

    private float apply(EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = this.getEquippedStack(slot);

        if (stack != null) {
            if (stack.getItem() instanceof ExtendedArmorItem armor) {
                //noinspection ConstantConditions
                if ((Entity)this instanceof LivingEntity) {
                    amount = armor.applyArmorToDamage((LivingEntity)((Entity)this), source, amount, stack);
                }
            }
        }
        return amount;
    }

    private float applyToAttacker(LivingEntity attacker, EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = attacker.getEquippedStack(slot);

        if (stack != null) {
            if (stack.getItem() instanceof ExtendedArmorItem armor) {
                //noinspection ConstantConditions
                if ((Entity)this instanceof LivingEntity) {
                    amount = armor.applyArmorToAttack((LivingEntity)((Entity)this), source, amount, stack);
                }
            }
        }
        return amount;
    }

    DamageSource source;

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "HEAD"))
    public void immersiveArmors$injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.source = source;
    }

    @ModifyArg(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
               index = 1)
    public float immersiveArmors$modifyArgs(float amount) {
        amount = apply(EquipmentSlot.HEAD, source, amount);
        amount = apply(EquipmentSlot.CHEST, source, amount);
        amount = apply(EquipmentSlot.LEGS, source, amount);
        amount = apply(EquipmentSlot.FEET, source, amount);

        Entity attacker = source.getAttacker();
        if (attacker instanceof LivingEntity) {
            amount = applyToAttacker((LivingEntity)attacker, EquipmentSlot.HEAD, source, amount);
            amount = applyToAttacker((LivingEntity)attacker, EquipmentSlot.CHEST, source, amount);
            amount = applyToAttacker((LivingEntity)attacker, EquipmentSlot.LEGS, source, amount);
            amount = applyToAttacker((LivingEntity)attacker, EquipmentSlot.FEET, source, amount);
        }

        return amount;
    }
}
