package immersive_armors.fabric.mixin;

import immersive_armors.util.DamageUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Unique
    DamageSource immersiveArmors$source;

    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At(value = "HEAD"))
    public void immersiveArmors$injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.immersiveArmors$source = source;
    }

    @ModifyArg(method = "hurt",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),
            index = 1)
    public float immersiveArmors$modifyArgs(float amount) {
        return DamageUtils.adjustDamage((LivingEntity) (Object) this, this.immersiveArmors$source, amount);
    }
}
