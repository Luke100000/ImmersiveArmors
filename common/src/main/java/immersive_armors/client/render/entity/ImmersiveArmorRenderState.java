package immersive_armors.client.render.entity;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface ImmersiveArmorRenderState {
    void immersiveArmors$setEntity(@Nullable LivingEntity entity, float tickDelta);

    @Nullable
    LivingEntity immersiveArmors$getEntity();

    float immersiveArmors$getTickDelta();
}
