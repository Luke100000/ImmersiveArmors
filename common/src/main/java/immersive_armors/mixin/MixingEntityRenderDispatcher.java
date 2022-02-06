package immersive_armors.mixin;

import java.util.Map;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderDispatcher.class)
public interface MixingEntityRenderDispatcher {
    @Accessor
    Map<EntityType<?>, EntityRenderer<?>> getRenderers();

    @Accessor
    Map<String, PlayerEntityRenderer> getModelRenderers();
}
