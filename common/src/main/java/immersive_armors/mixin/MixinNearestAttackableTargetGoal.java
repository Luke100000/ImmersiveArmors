package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class MixinNearestAttackableTargetGoal extends TargetGoal {
    public MixinNearestAttackableTargetGoal(Mob mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Inject(method = "start()V", at = @At("TAIL"))
    private void immersiveArmors$injectStart(CallbackInfo ci) {
        if (mob instanceof AbstractSkeleton && mob.getTarget() instanceof Player player) {
            int pieces = 0;
            for (ItemStack item : player.getArmorSlots()) {
                if (item.getItem() instanceof ExtendedArmorItem armor && armor.getExtendedMaterial().isAntiSkeleton()) {
                    pieces++;
                }
            }
            if (pieces >= 4) {
                mob.setTarget(null);
            }
        }
    }
}
