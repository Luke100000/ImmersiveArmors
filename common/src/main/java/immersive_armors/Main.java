package immersive_armors;

import immersive_armors.network.NetworkManager;
import net.minecraft.util.Identifier;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static NetworkManager networkManager;

    public static boolean FORGE = false;

    public static boolean ENABLE_EFFECTS = Config.getInstance().enableEffects;

    public static Identifier locate(String path) {
        return new Identifier(MOD_ID, path);
    }
}
