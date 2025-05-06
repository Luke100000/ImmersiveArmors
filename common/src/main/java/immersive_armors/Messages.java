package immersive_armors;

import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.c2s.ArmorCommandMessage;
import immersive_armors.network.s2c.SettingsMessage;

public class Messages {
    public static void bootstrap() {
        NetworkHandler.registerMessage(Main.MOD_ID, SettingsMessage.TYPE, SettingsMessage.STREAM_CODEC);
        NetworkHandler.registerMessage(Main.MOD_ID, ArmorCommandMessage.TYPE, ArmorCommandMessage.STREAM_CODEC);
    }
}
