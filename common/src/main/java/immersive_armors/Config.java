package immersive_armors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public final class Config implements Serializable {
    private static final long serialVersionUID = 9132405079466337851L;

    private static final Config INSTANCE = loadOrCreate();

    public static Config getInstance() {
        return INSTANCE;
    }

    public static final int VERSION = 1;

    public final HashMap<String, Boolean> enabledArmors = new HashMap<>();
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

    public int version = 0;

    public static File getConfigFile() {
        return new File("./config/" + Main.MOD_ID + ".json");
    }

    public void save() {
        try (FileWriter writer = new FileWriter(getConfigFile())) {
            version = VERSION;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config loadOrCreate() {
        try (FileReader reader = new FileReader(getConfigFile())) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Config config = gson.fromJson(reader, Config.class);
            if (config.version != VERSION) {
                config = new Config();
            }
            config.save();
            return config;
        } catch (IOException e) {
            //e.printStackTrace();
        }
        Config config = new Config();
        config.save();
        return config;
    }
}
