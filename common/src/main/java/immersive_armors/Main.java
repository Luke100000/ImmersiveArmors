package immersive_armors;

import immersive_armors.network.NetworkManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static immersive_armors.ItemPropertyOverwrite.applyItemOverwrite;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static NetworkManager networkManager;

    public static boolean FORGE = false;

    public static Config sharedConfig = Config.getInstance();
    private static Map<String, Float> backup = new HashMap<>();

    public static void setSharedConfig(Config config) {
        sharedConfig = config;

        // Reverting
        applyItemOverwrite(backup);

        // Applying properties overwrites
        backup = applyItemOverwrite(config.overwriteValues);
    }

    public static Identifier locate(String path) {
        return new Identifier(MOD_ID, path);
    }
}
