package immersive_armors.neoforge.cobalt.network;


import immersive_armors.cobalt.network.Message;
import immersive_armors.cobalt.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkHandlerImpl extends NetworkHandler.Impl {
    @SuppressWarnings("rawtypes")
    record MessageRegistryEntry(CustomPacketPayload.Type type,
                                StreamCodec codec,
                                IPayloadHandler serverPayloadHandler,
                                IPayloadHandler clientPayloadHandler) {
    }

    Map<String, List<MessageRegistryEntry>> messageRegistry = new HashMap<>();

    @Override
    public <T extends Message> void registerMessage(String namespace, CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, NetworkHandler.ClientHandler<T> clientHandler, NetworkHandler.ServerHandler<T> serverHandler) {
        messageRegistry.computeIfAbsent(namespace, k -> new LinkedList<>());
        IPayloadHandler<T> serverPayloadHandler =
                (m, c) -> serverHandler.handle(m, (ServerPlayer) c.player());
        IPayloadHandler<T> clientPayloadHandler =
                (m, c) -> clientHandler.handle(m);
        messageRegistry.get(namespace).add(new MessageRegistryEntry(type, codec, serverPayloadHandler, clientPayloadHandler));
    }

    @Override
    public void sendToServer(Message m) {
        ClientPacketDistributor.sendToServer(m);
    }

    @Override
    public void sendToPlayer(Message m, ServerPlayer e) {
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
                        entry.serverPayloadHandler,
                        entry.clientPayloadHandler
                ))
        );
    }
}
