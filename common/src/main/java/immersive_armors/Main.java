package immersive_armors;

import immersive_armors.config.Config;
import immersive_armors.network.NetworkManager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

import static immersive_armors.ItemPropertyOverwrite.applyItemOverride;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static NetworkManager networkManager;

    public static boolean FORGE = false;

    public static Config sharedConfig = Config.getInstance();
    private static Map<String, Float> backup = new HashMap<>();

    public static void setSharedConfig(Config config) {
        sharedConfig = config;

        // Reverting
        applyItemOverride(backup);

        // Applying properties overwrites
        backup = applyItemOverride(config.overwriteValues);
    }

    public static ResourceLocation locate(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
