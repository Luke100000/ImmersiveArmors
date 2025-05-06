package immersive_armors.cobalt.network;

import immersive_armors.Main;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public abstract class Message implements CustomPacketPayload {
    protected Message() {

    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> createType(String id) {
        return new CustomPacketPayload.Type<>(Main.locate(id));
    }

    public abstract void encode(RegistryFriendlyByteBuf b);

    public void receiveServer(ServerPlayer e) {

    }

    public void receiveClient() {

    }

    @Override
    abstract public CustomPacketPayload.Type<? extends CustomPacketPayload> type();
}
