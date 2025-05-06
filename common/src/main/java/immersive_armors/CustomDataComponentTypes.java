package immersive_armors;

import com.mojang.serialization.Codec;
import immersive_armors.armor_effects.SteamTechArmorEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class CustomDataComponentTypes {
    public static final ComponentType<Integer> SET_COUNT = register("set_count", builder -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<Long> LAST_DIVINE = register("last_divine", builder -> builder.codec(Codec.LONG).packetCodec(PacketCodecs.VAR_LONG));
    public static final ComponentType<SteamTechArmorEffect.ThrusterState> THRUSTER_STATE = register("thruster_state", builder -> builder.codec(SteamTechArmorEffect.ThrusterState.CODEC).packetCodec(SteamTechArmorEffect.ThrusterState.PACKET_CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }

    public static void bootstrap() {
        // Noop
    }
}

