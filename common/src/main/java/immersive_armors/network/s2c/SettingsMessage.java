package immersive_armors.network.s2c;

import immersive_armors.Config;
import immersive_armors.Main;
import immersive_armors.cobalt.network.Message;
import net.minecraft.entity.player.PlayerEntity;

public class SettingsMessage implements Message {
    public final boolean enableEffects;

    public SettingsMessage() {
        boolean enabled = Config.getInstance().enableEffects;
        Main.ENABLE_EFFECTS = enabled;
        this.enableEffects = enabled;
    }

    @Override
    public void receive(PlayerEntity e) {
        Main.networkManager.handleSettingsMessage(this);
    }
}
