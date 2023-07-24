package immersive_armors.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MixinMinecraftClient {
    @Accessor("paused")
    boolean getPaused();
    
    @Accessor("pausedTickDelta")
    float getPausedTickDelta();
}
