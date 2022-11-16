package immersive_armors.cobalt.registration;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class Registration {
    private static Impl INSTANCE;

    public static <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> obj) {
        return INSTANCE.register(registry, id, obj);
    }

    public static class ObjectBuilders {
        public static class ItemGroups {
            public static ItemGroup create(Identifier id, Supplier<ItemStack> icon) {
                return INSTANCE.itemGroup(id, icon);
            }
        }
    }

    public static abstract class Impl {
        protected Impl() {
            INSTANCE = this;
        }

        public abstract <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> obj);

        public abstract ItemGroup itemGroup(Identifier id, Supplier<ItemStack> icon);
    }
}
