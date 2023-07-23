package immersive_armors;

import immersive_armors.config.JsonConfig;

import java.util.HashMap;
import java.util.Map;

public final class Config extends JsonConfig {
    private static final Config INSTANCE = loadOrCreate();

    public static Config getInstance() {
        return INSTANCE;
    }

    public boolean hideSecondLayerUnderArmor = true;
    public boolean enableEffects = true;
    public boolean enableEnchantmentGlint = true;

    @SuppressWarnings("unused")
    public String _documentation = "https://github.com/Luke100000/ImmersiveArmors/blob/HEAD/config.md";
    public Map<String, Float> overwriteValues = new HashMap<>();

    public int hudClockXOffhand = -91 - 20 - 29;
    public int hudClockX = -91 - 20;
    public int hudClockY = -20;
    public int hudCompassXOffhand = 91 + 4 + 29;
    public int hudCompassX = 91 + 4;
    public int hudCompassY = -20;

    public float lootChance = 0.025f;
    public float mobEntityUseImmersiveArmorChance = 0.5f;

    public HashMap<String, Boolean> enabledArmors = new HashMap<>();

    {
        enabledArmors.put("bone", true);
        enabledArmors.put("wither", true);
        enabledArmors.put("warrior", true);
        enabledArmors.put("heavy", true);
        enabledArmors.put("robe", true);
        enabledArmors.put("slime", true);
        enabledArmors.put("divine", true);
        enabledArmors.put("prismarine", true);
        enabledArmors.put("wooden", true);
        enabledArmors.put("steampunk", true);
    }
}
