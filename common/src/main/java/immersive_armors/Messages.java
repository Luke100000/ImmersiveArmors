package immersive_armors;

import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.c2s.ArmorCommandMessage;
import immersive_armors.network.s2c.SettingsMessage;

public class Messages {
    public static void bootstrap() {
        NetworkHandler.registerMessage(SettingsMessage.class);
        NetworkHandler.registerMessage(ArmorCommandMessage.class);
    }
}
