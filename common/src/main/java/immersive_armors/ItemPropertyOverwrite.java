package immersive_armors;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ItemPropertyOverwrite {
    public static Map<String, Float> applyItemOverwrite(Map<String, Float> map) {
        Map<String, Float> backup = new HashMap<>();
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String[] split = entry.getKey().split(":");
            if (split.length == 2) {
                Optional<ExtendedArmorMaterial> found = Items.items.values().stream()
                        .map(Supplier::get)
                        .filter(i -> i instanceof ExtendedArmorItem)
                        .map(i -> ((ExtendedArmorItem)i).getMaterial())
                        .filter(i -> i.getName().equals(split[0])).findAny();
                if (found.isPresent()) {
                    ExtendedArmorMaterial material = found.get();
                    switch (split[1]) {
                        case "helmetProtection" -> {
                            backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[3]);
                            material.getProtectionAmounts()[3] = entry.getValue().intValue();
                        }
                        case "chestplateProtection" -> {
                            backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[2]);
                            material.getProtectionAmounts()[2] = entry.getValue().intValue();
                        }
                        case "leggingsProtection" -> {
                            backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[1]);
                            material.getProtectionAmounts()[1] = entry.getValue().intValue();
                        }
                        case "bootsProtection" -> {
                            backup.putIfAbsent(entry.getKey(), (float)material.getProtectionAmounts()[0]);
                            material.getProtectionAmounts()[0] = entry.getValue().intValue();
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
                            backup.putIfAbsent(entry.getKey(), (float)material.getEnchantability());
                            material.enchantability(entry.getValue().intValue());
                        }
                        case "durabilityMultiplier" -> {
                            backup.putIfAbsent(entry.getKey(), (float)material.getDurabilityMultiplier());
                            material.durabilityMultiplier(entry.getValue().intValue());
                        }
                        default -> Config.LOGGER.error("Armor property " + split[1] + " for item " + split[0] + " does not exist!");
                    }

                    // Refresh properties
                    Items.items.values().stream()
                            .map(Supplier::get)
                            .filter(i -> i instanceof ExtendedArmorItem && ((ExtendedArmorItem)i).getMaterial() == material)
                            .forEach(i -> ((ExtendedArmorItem)i).refreshAttributes());
                } else {
                    Config.LOGGER.error("Item " + split[0] + " for armor property overwrite does not exist!");
                }
            } else {
                Config.LOGGER.error("Malformed armor property overwrite: " + entry.getKey());
            }
        }
        return backup;
    }
}
