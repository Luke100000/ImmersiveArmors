package immersive_armors.network;

import immersive_armors.network.s2c.SettingsMessage;

public interface NetworkManager {
    void handleSettingsMessage(SettingsMessage message);
}
