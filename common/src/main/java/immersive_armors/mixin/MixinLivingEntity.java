package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    protected MixinLivingEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private float immersiveArmors$Apply(EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = this.getItemBySlot(slot);

        //noinspection ConstantConditions
        if (stack != null && stack.getItem() instanceof ExtendedArmorItem armor && (Entity) this instanceof LivingEntity livingEntity) {
            amount = armor.applyArmorToDamage(livingEntity, source, amount, stack);
        }

        return amount;
    }

    @Unique
    private float immersiveArmors$ApplyToAttacker(LivingEntity attacker, EquipmentSlot slot, DamageSource source, float amount) {
        ItemStack stack = attacker.getItemBySlot(slot);

        //noinspection ConstantConditions
        if (stack != null && stack.getItem() instanceof ExtendedArmorItem armor && (Entity) this instanceof LivingEntity livingEntity) {
            amount = armor.applyArmorToAttack(livingEntity, source, amount, stack);
        }

        return amount;
    }

    @Unique
    DamageSource immersiveArmors$source;

    @Inject(method = "hurt", at = @At(value = "HEAD"))
    public void immersiveArmors$injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.immersiveArmors$source = source;
    }

    @ModifyArg(method = "hurt",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),
            index = 1)
    public float immersiveArmors$modifyArgs(float amount) {
        amount = immersiveArmors$Apply(EquipmentSlot.HEAD, immersiveArmors$source, amount);
        amount = immersiveArmors$Apply(EquipmentSlot.CHEST, immersiveArmors$source, amount);
        amount = immersiveArmors$Apply(EquipmentSlot.LEGS, immersiveArmors$source, amount);
        amount = immersiveArmors$Apply(EquipmentSlot.FEET, immersiveArmors$source, amount);

        Entity attacker = immersiveArmors$source.getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            amount = immersiveArmors$ApplyToAttacker(livingAttacker, EquipmentSlot.HEAD, immersiveArmors$source, amount);
            amount = immersiveArmors$ApplyToAttacker(livingAttacker, EquipmentSlot.CHEST, immersiveArmors$source, amount);
            amount = immersiveArmors$ApplyToAttacker(livingAttacker, EquipmentSlot.LEGS, immersiveArmors$source, amount);
            amount = immersiveArmors$ApplyToAttacker(livingAttacker, EquipmentSlot.FEET, immersiveArmors$source, amount);
        }

        return amount;
    }
}
