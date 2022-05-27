package immersive_armors;

import immersive_armors.network.NetworkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static final Logger LOGGER = LogManager.getLogger();
    public static NetworkManager networkManager;

    public static boolean FORGE = false;

    public static boolean ENABLE_EFFECTS = Config.getInstance().enableEffects;
}
