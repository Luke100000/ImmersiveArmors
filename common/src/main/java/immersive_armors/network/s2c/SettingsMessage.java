package immersive_armors.network.s2c;

import immersive_armors.config.Config;
import immersive_armors.Main;
import immersive_armors.cobalt.network.Message;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class SettingsMessage extends Message {
    public final Config config;

    public SettingsMessage() {
        Main.setSharedConfig(Config.getInstance());
        this.config = Config.getInstance();
    }

    public SettingsMessage(PacketByteBuf b) {
        config = Config.fromJsonString(b.readString());
    }

    @Override
    public void encode(PacketByteBuf b) {
        b.writeString(config.toJsonString());
    }

    @Override
    public void receive(PlayerEntity e) {
        Main.networkManager.handleSettingsMessage(this);
    }
}
