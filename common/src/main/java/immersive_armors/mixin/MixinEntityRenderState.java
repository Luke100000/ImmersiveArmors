package immersive_armors.mixin;

import immersive_armors.client.render.entity.ImmersiveArmorRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class MixinEntityRenderState implements ImmersiveArmorRenderState {
    @Unique
    private LivingEntity immersiveArmors$entity;
    @Unique
    private float immersiveArmors$tickDelta;

    @Override
    public void immersiveArmors$setEntity(@Nullable LivingEntity entity, float tickDelta) {
        this.immersiveArmors$entity = entity;
        this.immersiveArmors$tickDelta = tickDelta;
    }

    @Override
    public @Nullable LivingEntity immersiveArmors$getEntity() {
        return immersiveArmors$entity;
    }

    @Override
    public float immersiveArmors$getTickDelta() {
        return immersiveArmors$tickDelta;
    }
}
