package immersive_armors;

import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.item.ArmorItem;

public class ItemPropertyOverwrite {
    public static Map<String, Float> applyItemOverride(Map<String, Float> map) {
        Map<String, Float> backup = new HashMap<>();
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String[] split = entry.getKey().split(":");
            if (split.length == 2) {
                Optional<ExtendedArmorMaterial> found = Items.items.values().stream()
                        .map(Supplier::get)
                        .filter(ExtendedArmorItem.class::isInstance)
                        .map(i -> ((ExtendedArmorItem) i).getExtendedMaterial())
                        .filter(i -> i.getName().equals(split[0])).findAny();
                if (found.isPresent()) {
                    ExtendedArmorMaterial material = found.get();
                    EnumMap<ArmorItem.Type, Integer> protection = material.getProtection();
                    switch (split[1]) {
                        case "helmetProtection" -> {
                            backup.putIfAbsent(entry.getKey(), protection.get(ArmorItem.Type.HELMET).floatValue());
                            protection.put(ArmorItem.Type.HELMET, entry.getValue().intValue());
                        }
                        case "chestplateProtection" -> {
                            backup.putIfAbsent(entry.getKey(), protection.get(ArmorItem.Type.CHESTPLATE).floatValue());
                            protection.put(ArmorItem.Type.CHESTPLATE, entry.getValue().intValue());
                        }
                        case "leggingsProtection" -> {
                            backup.putIfAbsent(entry.getKey(), protection.get(ArmorItem.Type.LEGGINGS).floatValue());
                            protection.put(ArmorItem.Type.LEGGINGS, entry.getValue().intValue());
                        }
                        case "bootsProtection" -> {
                            backup.putIfAbsent(entry.getKey(), protection.get(ArmorItem.Type.BOOTS).floatValue());
                            protection.put(ArmorItem.Type.BOOTS, entry.getValue().intValue());
                        }
                        case "weight" -> {
                            backup.putIfAbsent(entry.getKey(), material.getWeight());
                            material.weight(entry.getValue());
                        }
                        case "toughness" -> {
                            backup.putIfAbsent(entry.getKey(), material.getToughness());
                            material.toughness(entry.getValue());
                        }
                        case "enchantability" -> {
                            backup.putIfAbsent(entry.getKey(), (float) material.getEnchantability());
                            material.enchantability(entry.getValue().intValue());
                        }
                        default ->
                                Config.LOGGER.error("Armor property {} for item {} does not exist!", split[1], split[0]);
                    }

                    // Refresh properties
                    Items.items.values().stream()
                            .map(Supplier::get)
                            .filter(i -> i instanceof ExtendedArmorItem && ((ExtendedArmorItem) i).getExtendedMaterial() == material)
                            .forEach(i -> ((ExtendedArmorItem) i).refreshAttributes());
                } else {
                    Config.LOGGER.error("Item {} for armor property overwrite does not exist!", split[0]);
                }
            } else {
                Config.LOGGER.error("Malformed armor property overwrite: {}", entry.getKey());
            }
        }
        return backup;
    }
}
