package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class MixinFollowTargetGoal extends TrackTargetGoal {
    public MixinFollowTargetGoal(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Inject(method = "start()V", at = @At("TAIL"))
    private void immersiveArmors$injectStart(CallbackInfo ci) {
        if (mob instanceof AbstractSkeletonEntity && mob.getTarget() instanceof PlayerEntity player) {
            int pieces = 0;
            for (ItemStack item : player.getArmorItems()) {
                if (item.getItem() instanceof ExtendedArmorItem armor && armor.getMaterial().isAntiSkeleton()) {
                    pieces++;
                }
            }
            if (pieces >= 4) {
                mob.setTarget(null);
            }
        }
    }
}
