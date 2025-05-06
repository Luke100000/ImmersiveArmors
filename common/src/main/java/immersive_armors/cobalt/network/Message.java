package immersive_armors.cobalt.network;

import immersive_armors.Main;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class Message implements CustomPayload {
    protected Message() {

    }

    public static <T extends CustomPayload> CustomPayload.Id<T> createType(String id) {
        return new CustomPayload.Id<>(Main.locate(id));
    }

    public abstract void encode(RegistryByteBuf b);

    public void receiveServer(ServerPlayerEntity e) {

    }

    public void receiveClient() {

    }

    @Override
    abstract public CustomPayload.Id<? extends CustomPayload> getId();
}
