package immersive_armors.config;

import immersive_armors.Items;
import immersive_armors.config.configEntries.FloatConfigEntry;
import immersive_armors.config.configEntries.IntegerConfigEntry;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public class ConfigScreen {
    public static Screen getScreen() {
        Config config = Config.getInstance();

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(new TranslatableText("itemGroup.immersive_armors.immersive_armors_tab"))
                .setSavingRunnable(config::save);

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("option.immersive_armors.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // regular fields
        for (Field field : Config.class.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                try {
                    String key = "option.immersive_armors." + field.getName();
                    if (annotation instanceof IntegerConfigEntry entry) {
                        general.addEntry(entryBuilder.startIntField(new TranslatableText(key), field.getInt(config))
                                .setDefaultValue(entry.value())
                                .setSaveConsumer(v -> {
                                    try {
                                        field.setInt(config, v);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .setMin(entry.min())
                                .setMax(entry.max())
                                .build());
                    } else if (annotation instanceof FloatConfigEntry entry) {
                        general.addEntry(entryBuilder.startFloatField(new TranslatableText(key), field.getFloat(config))
                                .setDefaultValue(entry.value())
                                .setSaveConsumer(v -> {
                                    try {
                                        field.setFloat(config, v);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .setMin(entry.min())
                                .setMax(entry.max())
                                .build());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // whitelist
        ConfigCategory whitelist = builder.getOrCreateCategory(new TranslatableText("option.immersive_armors.whitelist"));
        List<String> materials = Items.items.values().stream().map(Supplier::get).map(i -> (ExtendedArmorItem)i).map(ExtendedArmorItem::getMaterial).map(ExtendedArmorMaterial::getName).distinct().sorted().toList();

        for (String material : materials) {
            config.enabledArmors.putIfAbsent(material, true);
        }

        for (String material : config.enabledArmors.keySet()) {
            whitelist.addEntry(entryBuilder.startBooleanToggle(new LiteralText(material), config.enabledArmors.getOrDefault(material, true))
                    .setDefaultValue(true)
                    .setSaveConsumer(v -> config.enabledArmors.put(material, v))
                    .requireRestart()
                    .build());
        }

        return builder.build();
    }
}
