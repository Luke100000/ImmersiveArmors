package immersive_armors.cobalt.registration;


import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class Registration {
    private static Impl INSTANCE;

    public static <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj) {
        return INSTANCE.register(registry, id, obj);
    }

    public abstract static class Impl {
        protected Impl() {
            INSTANCE = this;
        }

        public abstract <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj);
    }
}
