package immersive_armors.fabric.cobalt.network;

import immersive_armors.cobalt.network.Message;
import immersive_armors.cobalt.network.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class NetworkHandlerImpl extends NetworkHandler.Impl {
    @Override
    public <T extends Message> void registerMessage(String namespace, CustomPayload.Id<T> type, PacketCodec<RegistryByteBuf, T> codec, NetworkHandler.ClientHandler<T> clientHandler, NetworkHandler.ServerHandler<T> serverHandler) {
        if (clientHandler != null) PayloadTypeRegistry.playS2C().register(type, codec);
        if (serverHandler != null) PayloadTypeRegistry.playC2S().register(type, codec);

        if (clientHandler != null && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientProxy.register(type, clientHandler);
        }

        if (serverHandler != null) {
            ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> serverHandler.handle(payload, context.player()));
        }
    }

    @Override
    public void sendToServer(Message msg) {
        ClientProxy.sendToServer(msg);
    }

    @Override
    public void sendToPlayer(Message msg, ServerPlayerEntity e) {
        RegistryByteBuf buf = new RegistryByteBuf(Unpooled.buffer(), e.getRegistryManager());
        msg.encode(buf);
        ServerPlayNetworking.send(e, msg);
    }

    @Override
    public void sendToTrackingPlayers(Message msg, Entity e) {
        RegistryByteBuf buf = new RegistryByteBuf(Unpooled.buffer(), e.getRegistryManager());
        msg.encode(buf);
        for (ServerPlayerEntity player : PlayerLookup.tracking(e)) {
            ServerPlayNetworking.send(player, msg);
        }
    }

    // Prevent eager loading client side code
    private static final class ClientProxy {
        private ClientProxy() {
            // Nop
        }

        public static <T extends Message> void register(CustomPayload.Id<T> type, NetworkHandler.ClientHandler<T> handler) {
            ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.handle(payload));
        }

        public static void sendToServer(Message msg) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                RegistryByteBuf buf = new RegistryByteBuf(Unpooled.buffer(), player.getRegistryManager());
                msg.encode(buf);
                ClientPlayNetworking.send(msg);
            }
        }
    }
}


