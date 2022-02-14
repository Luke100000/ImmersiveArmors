package immersive_armors.mixin;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MemoryModuleType.class)
public interface MixinMemoryModuleType {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Invoker("<init>")
    static <U> MemoryModuleType<U> init(Optional<Codec<U>> codec) {
        return null;
    }
}
