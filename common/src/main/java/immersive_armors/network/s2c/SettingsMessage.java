package immersive_armors.network.s2c;

import immersive_armors.Main;
import immersive_armors.cobalt.network.Message;
import immersive_armors.config.Config;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class SettingsMessage extends Message {
    public static final PacketCodec<RegistryByteBuf, SettingsMessage> STREAM_CODEC = PacketCodec.of(SettingsMessage::encode, SettingsMessage::new);
    public static final CustomPayload.Id<SettingsMessage> TYPE = Message.createType("settings");

    public final Config config;

    public SettingsMessage() {
        Main.setSharedConfig(Config.getInstance());
        this.config = Config.getInstance();
    }

    public SettingsMessage(RegistryByteBuf b) {
        config = Config.fromJsonString(b.readString());
    }

    @Override
    public void encode(RegistryByteBuf b) {
        b.writeString(config.toJsonString());
    }

    @Override
    public void receiveClient() {
        Main.networkManager.handleSettingsMessage(this);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return TYPE;
    }
}
