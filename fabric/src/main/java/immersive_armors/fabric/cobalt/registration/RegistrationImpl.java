package immersive_armors.fabric.cobalt.registration;

import immersive_armors.cobalt.registration.Registration;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class RegistrationImpl extends Registration.Impl {
    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj) {
        T register = Registry.register(registry, id, obj.get());
        return () -> register;
    }
}
