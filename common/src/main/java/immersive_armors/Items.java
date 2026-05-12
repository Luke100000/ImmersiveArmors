package immersive_armors;

import immersive_armors.armor_effects.*;
import immersive_armors.cobalt.registration.Registration;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.*;
import java.util.function.Supplier;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.Ingredient;

public interface Items {
    Map<String, Supplier<Item>> coloredItems = new HashMap<>();
    Map<String, Supplier<Item>> items = new HashMap<>();
    List<Supplier<Item>> itemsOrdered = new LinkedList<>();
    Map<String, Map<Supplier<Item>, Float>> lootLookup = new HashMap<>();

    ExtendedArmorMaterial BONE_ARMOR = registerSet(new ExtendedArmorMaterial("bone")
            .addLoot("minecraft:chests/village/village_weaponsmith", 1.0f)
            .addLoot("minecraft:chests/jungle_temple", 1.0f)
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.BONE))
            .protectionAmount(1, 3, 2, 1)
            .enchantability(15)
            .equipSound(SoundEvents.SKELETON_AMBIENT)
            .antiSkeleton()
            .weight(-0.02f));

    ExtendedArmorMaterial WITHER_ARMOR = registerSet(new ExtendedArmorMaterial("wither")
            .addLoot("minecraft:chests/jungle_temple", 0.5f)
            .addLoot("minecraft:chests/ruined_portal", 1.0f)
            .addLoot("minecraft:chests/bastion_other", 1.0f)
            .durabilityMultiplier(12)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.BONE))
            .protectionAmount(2, 4, 3, 2)
            .enchantability(0)
            .effect(new WitherArmorEffect(0.25f, 20))
            .hideCape()
            .equipSound(SoundEvents.WITHER_SKELETON_AMBIENT)
            .antiSkeleton()
            .weight(-0.01f));

    ExtendedArmorMaterial WARRIOR_ARMOR = registerSet(new ExtendedArmorMaterial("warrior")
            .addLoot("minecraft:chests/village/village_armorer", 1.0f)
            .addLoot("minecraft:chests/shipwreck_supply", 1.0f)
            .protectionAmount(2, 5, 6, 2)
            .durabilityMultiplier(15)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT))
            .toughness(1.0f)
            .enchantability(5)
            .hideCape()
            .effect(new BerserkArmorEffect(0.2f))
            .effect(new WeaponEfficiency(0.05f, Main.locate("axes"), "axe"))
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial HEAVY_ARMOR = registerSet(new ExtendedArmorMaterial("heavy")
            .addLoot("minecraft:chests/village/village_armorer", 1.0f)
            .addLoot("minecraft:chests/stronghold_crossing", 1.0f)
            .protectionAmount(4, 6, 5, 3)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT))
            .toughness(4.0f)
            .knockbackReduction(0.5f)
            .weight(0.05f)
            .enchantability(6)
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial ROBE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("robe")
            .addLoot("minecraft:chests/village/village_shepherd", 0.25f)
            .addLoot("minecraft:chests/woodland_mansion", 1.0f)
            .addLoot("minecraft:chests/igloo_chest", 1.0f)
            .protectionAmount(2, 3, 2, 1)
            .enchantability(50)
            .durabilityMultiplier(14)
            .repairIngredient(() -> Ingredient.of(ItemTags.WOOL))
            .color(0xFFB00F46)
            .effect(new FireResistanceArmorEffect(0.25f))
            .effect(new FireInflictingArmorEffect(20))
            .effect(new MagicProtectionArmorEffect(0.2f))
            .equipSound(SoundEvents.WOOL_PLACE));

    ExtendedArmorMaterial SLIME_ARMOR = registerSet(new ExtendedArmorMaterial("slime")
            .addLoot("minecraft:chests/simple_dungeon", 0.25f)
            .protectionAmount(3, 5, 4, 2)
            .enchantability(10)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.SLIME_BALL))
            .knockbackReduction(0.25f)
            .effect(new BouncingArmorEffect(0.25f))
            .effect(new ExplosionProtectionArmorEffect(0.2f))
            .equipSound(SoundEvents.SLIME_SQUISH));

    ExtendedArmorMaterial DIVINE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("divine")
            .addLoot("minecraft:chests/village/village_temple", 0.4f)
            .addLoot("minecraft:chests/bastion_treasure", 0.25f)
            .addLoot("minecraft:chests/woodland_mansion", 0.25f)
            .addLoot("minecraft:chests/desert_pyramid", 1.0f)
            .protectionAmount(3, 7, 5, 3)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT))
            .enchantability(30)
            .effect(new DivineArmorEffect(1200))
            .color(0xFFB00F46)
            .hideCape()
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial PRISMARINE_ARMOR = registerSet(new ExtendedArmorMaterial("prismarine")
            .addLoot("minecraft:chests/underwater_ruin_big", 1.0f)
            .addLoot("minecraft:chests/underwater_ruin_small", 0.5f)
            .protectionAmount(3, 8, 6, 3)
            .enchantability(8)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.PRISMARINE_CRYSTALS))
            .weight(0.02f)
            .waterMovement(0.25f)
            .effect(new SpikesArmorEffect(1))
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial WOODEN_ARMOR = registerSet(new ExtendedArmorMaterial("wooden")
            .addLoot("minecraft:chests/village/village_fletcher", 0.25f)
            .protectionAmount(1, 3, 2, 1)
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.of(ItemTags.LOGS))
            .enchantability(4)
            .effect(new ArrowBlockArmorEffect(0.15f))
            .effect(new ExplosionProtectionArmorEffect(0.1f))
            .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER));

    ExtendedArmorMaterial STEAMPUNK_ARMOR = registerSet(new ExtendedArmorMaterial("steampunk")
            .addLoot("minecraft:chests/village/village_toolsmith", 0.25f)
            .addLoot("minecraft:chests/shipwreck_treasure", 1.0f)
            .protectionAmount(3, 6, 3, 2)
            .durabilityMultiplier(10)
            .repairIngredient(() -> Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT))
            .enchantability(4)
            .hideCape()
            .effect(new ExplosionProtectionArmorEffect(0.1f))
            .effect(new SteamTechArmorEffect())
            .equipSound(SoundEvents.REDSTONE_TORCH_BURNOUT));

    static void bootstrap() {
        ItemGroups.markItemsReady();
    }

    static ExtendedArmorMaterial registerSet(ExtendedArmorMaterial material) {
        material.registerVanillaMaterial();

        Items.items.putAll(register(material.getName() + "_helmet", () -> new ExtendedArmorItem(baseProps(), ArmorItem.Type.HELMET, material), material));
        Items.items.putAll(register(material.getName() + "_chestplate", () -> new ExtendedArmorItem(baseProps(), ArmorItem.Type.CHESTPLATE, material), material));
        Items.items.putAll(register(material.getName() + "_leggings", () -> new ExtendedArmorItem(baseProps(), ArmorItem.Type.LEGGINGS, material), material));
        Items.items.putAll(register(material.getName() + "_boots", () -> new ExtendedArmorItem(baseProps(), ArmorItem.Type.BOOTS, material), material));

        return material;
    }

    static ExtendedArmorMaterial registerDyeableSet(ExtendedArmorMaterial material) {
        material.registerVanillaMaterial();

        Items.coloredItems.putAll(register(material.getName() + "_helmet", () -> new DyeableExtendedArmorItem(baseProps(), ArmorItem.Type.HELMET, material), material));
        Items.coloredItems.putAll(register(material.getName() + "_chestplate", () -> new DyeableExtendedArmorItem(baseProps(), ArmorItem.Type.CHESTPLATE, material), material));
        Items.coloredItems.putAll(register(material.getName() + "_leggings", () -> new DyeableExtendedArmorItem(baseProps(), ArmorItem.Type.LEGGINGS, material), material));
        Items.coloredItems.putAll(register(material.getName() + "_boots", () -> new DyeableExtendedArmorItem(baseProps(), ArmorItem.Type.BOOTS, material), material));

        Items.items.putAll(Items.coloredItems);

        return material;
    }

    static Map<String, Supplier<Item>> register(String name, Supplier<Item> item, ExtendedArmorMaterial material) {
        Supplier<Item> register = Registration.register(BuiltInRegistries.ITEM, Main.locate(name), item);
        for (Map.Entry<String, Float> entry : material.getLoot().entrySet()) {
            lootLookup.putIfAbsent(entry.getKey(), new HashMap<>());
            lootLookup.get(entry.getKey()).put(register, entry.getValue());
        }
        itemsOrdered.add(register);
        return Collections.singletonMap(name, register);
    }

    static Item.Properties baseProps() {
        return new Item.Properties().stacksTo(1);
    }

    static List<ItemStack> getSortedItems() {
        return itemsOrdered.stream().map(i -> i.get().getDefaultInstance()).toList();
    }

    static ItemColor getDyeColor(int defaultColor) {
        return (item, layer) -> {
            if (layer != 0) {
                return -1;
            } else {
                return DyedItemColor.getOrDefault(item, defaultColor);
            }
        };
    }
}
