package immersive_armors;

import com.mojang.serialization.Codec;
import immersive_armors.armor_effects.SteamTechArmorEffect;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class CustomDataComponentTypes {
    public static final DataComponentType<Integer> SET_COUNT = register("set_count", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final DataComponentType<Long> LAST_DIVINE = register("last_divine", builder -> builder.persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG));
    public static final DataComponentType<SteamTechArmorEffect.ThrusterState> THRUSTER_STATE = register("thruster_state", builder -> builder.persistent(SteamTechArmorEffect.ThrusterState.CODEC).networkSynchronized(SteamTechArmorEffect.ThrusterState.PACKET_CODEC));

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void bootstrap() {
        // Noop
    }
}

