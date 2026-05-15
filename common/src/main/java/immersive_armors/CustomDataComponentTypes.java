package immersive_armors;

import com.mojang.serialization.Codec;
import immersive_armors.armor_effects.SteamTechArmorEffect;
import immersive_armors.cobalt.registration.Registration;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class CustomDataComponentTypes {
    public static final Supplier<DataComponentType<Integer>> SET_COUNT = register("set_count", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final Supplier<DataComponentType<Long>> LAST_DIVINE = register("last_divine", builder -> builder.persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG));
    public static final Supplier<DataComponentType<SteamTechArmorEffect.ThrusterState>> THRUSTER_STATE = register("thruster_state", builder -> builder.persistent(SteamTechArmorEffect.ThrusterState.CODEC).networkSynchronized(SteamTechArmorEffect.ThrusterState.PACKET_CODEC));

    private static <T> Supplier<DataComponentType<T>> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registration.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Main.locate(id), () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void bootstrap() {
        // Noop
    }
}
