package immersive_armors.config;

import immersive_armors.config.configEntries.FloatConfigEntry;
import immersive_armors.config.configEntries.IntegerConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ConfigScreen {
    public static Screen getScreen() {
        Config config = Config.getInstance();

        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.translatable("itemGroup.immersive_armors.immersive_armors_tab"))
                .setSavingRunnable(config::save);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("option.immersive_armors.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // regular fields
        for (Field field : Config.class.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                try {
                    String key = "option.immersive_armors." + field.getName();
                    if (annotation instanceof IntegerConfigEntry entry) {
                        general.addEntry(entryBuilder.startIntField(Component.translatable(key), field.getInt(config))
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
                        general.addEntry(entryBuilder.startFloatField(Component.translatable(key), field.getFloat(config))
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

        return builder.build();
    }
}
