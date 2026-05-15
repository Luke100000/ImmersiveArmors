package immersive_armors.neoforge.cobalt.registration;


import immersive_armors.cobalt.registration.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Contains all the crap required to interface with forge's code
 */
public class RegistrationImpl extends Registration.Impl {
    private final Map<String, RegistryRepo> repos = new HashMap<>();

    private final IEventBus modBus;

    public RegistrationImpl(IEventBus modBus) {
        this.modBus = modBus;
    }

    private RegistryRepo getRepo(String namespace) {
        return repos.computeIfAbsent(namespace, RegistryRepo::new);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> obj) {
        DeferredRegister reg = getRepo(id.getNamespace()).get(registry);
        return reg.register(id.getPath(), obj);
    }

    class RegistryRepo {
        private final Map<Identifier, DeferredRegister<?>> registries = new HashMap<>();

        private final String namespace;

        public RegistryRepo(String namespace) {
            this.namespace = namespace;
        }

        @SuppressWarnings({"rawtypes"})
        public <T> DeferredRegister get(Registry<? super T> registry) {
            Identifier id = registry.key().identifier();
            if (!registries.containsKey(id)) {
                DeferredRegister def = DeferredRegister.create(registry, namespace);

                def.register(modBus);

                registries.put(id, def);
            }

            return registries.get(id);
        }
    }
}
