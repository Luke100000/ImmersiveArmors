package immersive_armors.cobalt.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class NetworkHandler {
    private static Impl INSTANCE;

    public interface ClientHandler<T extends Message> {
        void handle(T message);
    }

    public interface ServerHandler<T extends Message> {
        void handle(T message, ServerPlayerEntity player);
    }

    public static <T extends Message> void handleDefault(T message, ServerPlayerEntity e) {
        message.receiveServer(e);
    }

    public static <T extends Message> void handleDefault(T message) {
        message.receiveClient();
    }

    public static <T extends Message> void registerMessage(String namespace, CustomPayload.Id<T> type, PacketCodec<RegistryByteBuf, T> codec) {
        registerMessage(namespace, type, codec, NetworkHandler::handleDefault, NetworkHandler::handleDefault);
    }

    public static <T extends Message> void registerMessage(String namespace, CustomPayload.Id<T> type, PacketCodec<RegistryByteBuf, T> codec, NetworkHandler.ClientHandler<T> clientHandler, NetworkHandler.ServerHandler<T> serverHandler) {
        INSTANCE.registerMessage(namespace, type, codec, clientHandler, serverHandler);
    }

    public static void sendToServer(Message m) {
        INSTANCE.sendToServer(m);
    }

    public static void sendToPlayer(Message m, ServerPlayerEntity e) {
        INSTANCE.sendToPlayer(m, e);
    }

    public static void sendToTrackingPlayers(Message m, Entity origin) {
        INSTANCE.sendToTrackingPlayers(m, origin);
    }

    public abstract static class Impl {
        protected Impl() {
            INSTANCE = this;
        }

        public abstract <T extends Message> void registerMessage(String namespace, CustomPayload.Id<T> type, PacketCodec<RegistryByteBuf, T> codec, NetworkHandler.ClientHandler<T> clientHandler, NetworkHandler.ServerHandler<T> serverHandler);

        public abstract void sendToServer(Message m);

        public abstract void sendToPlayer(Message m, ServerPlayerEntity e);

        public abstract void sendToTrackingPlayers(Message m, Entity origin);
    }
}
