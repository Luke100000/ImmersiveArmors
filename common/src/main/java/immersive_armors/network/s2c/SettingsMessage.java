package immersive_armors.network.s2c;

import immersive_armors.Main;
import immersive_armors.cobalt.network.Message;
import immersive_armors.config.Config;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class SettingsMessage extends Message {
    public static final StreamCodec<RegistryFriendlyByteBuf, SettingsMessage> STREAM_CODEC = StreamCodec.ofMember(SettingsMessage::encode, SettingsMessage::new);
    public static final CustomPacketPayload.Type<SettingsMessage> TYPE = Message.createType("settings");

    public final Config config;

    public SettingsMessage() {
        Main.setSharedConfig(Config.getInstance());
        this.config = Config.getInstance();
    }

    public SettingsMessage(RegistryFriendlyByteBuf b) {
        config = Config.fromJsonString(b.readUtf());
    }

    @Override
    public void encode(RegistryFriendlyByteBuf b) {
        b.writeUtf(config.toJsonString());
    }

    @Override
    public void receiveClient() {
        Main.networkManager.handleSettingsMessage(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
