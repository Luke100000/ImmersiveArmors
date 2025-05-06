package immersive_armors.neoforge.cobalt.network;


import immersive_armors.cobalt.network.Message;
import immersive_armors.cobalt.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkHandlerImpl extends NetworkHandler.Impl {
    @SuppressWarnings("rawtypes")
    record MessageRegistryEntry(CustomPayload.Id type,
                                PacketCodec codec,
                                DirectionalPayloadHandler payloadHandler) {
    }

    Map<String, List<MessageRegistryEntry>> messageRegistry = new HashMap<>();

    @Override
    public <T extends Message> void registerMessage(String namespace, CustomPayload.Id<T> type, PacketCodec<RegistryByteBuf, T> codec, NetworkHandler.ClientHandler<T> clientHandler, NetworkHandler.ServerHandler<T> serverHandler) {
        messageRegistry.computeIfAbsent(namespace, k -> new LinkedList<>());
        DirectionalPayloadHandler<T> payloadHandler = new DirectionalPayloadHandler<>(
                (m, c) -> clientHandler.handle(m),
                (m, c) -> serverHandler.handle(m, (ServerPlayerEntity) c.player())
        );
        messageRegistry.get(namespace).add(new MessageRegistryEntry(type, codec, payloadHandler));
    }

    @Override
    public void sendToServer(Message m) {
        PacketDistributor.sendToServer(m);
    }

    @Override
    public void sendToPlayer(Message m, ServerPlayerEntity e) {
        PacketDistributor.sendToPlayer(e, m);
    }

    @Override
    public void sendToTrackingPlayers(Message m, Entity origin) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(origin, m);
    }

    public void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        //noinspection unchecked
        messageRegistry.values().forEach(channel ->
                channel.forEach(entry -> registrar.playBidirectional(
                        entry.type,
                        entry.codec,
                        entry.payloadHandler
                ))
        );
    }
}
