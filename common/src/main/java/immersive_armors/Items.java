package immersive_armors;

import immersive_armors.armorEffects.*;
import immersive_armors.cobalt.registration.Registration;
import immersive_armors.config.Config;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface Items {
    Map<String, Supplier<Item>> coloredItems = new HashMap<>();
    Map<String, Supplier<Item>> items = new HashMap<>();
    Map<String, Map<Supplier<Item>, Float>> lootLookup = new HashMap<>();

    ExtendedArmorMaterial BONE_ARMOR = registerSet(new ExtendedArmorMaterial("bone")
            .addLoot("minecraft:chests/village/village_weaponsmith", 1.0f)
            .addLoot("minecraft:chests/jungle_temple", 1.0f)
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.BONE))
            .protectionAmount(1, 3, 2, 1)
            .enchantability(15)
            .equipSound(SoundEvents.ENTITY_SKELETON_AMBIENT)
            .antiSkeleton()
            .weight(-0.02f));

    ExtendedArmorMaterial WITHER_ARMOR = registerSet(new ExtendedArmorMaterial("wither")
            .addLoot("minecraft:chests/jungle_temple", 0.5f)
            .addLoot("minecraft:chests/ruined_portal", 1.0f)
            .addLoot("minecraft:chests/bastion_other", 1.0f)
            .durabilityMultiplier(12)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.BONE))
            .protectionAmount(2, 4, 3, 2)
            .enchantability(0)
            .effect(new WitherArmorEffect(1.0f, 10))
            .hideCape()
            .equipSound(SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT)
            .antiSkeleton()
            .weight(-0.01f));

    ExtendedArmorMaterial WARRIOR_ARMOR = registerSet(new ExtendedArmorMaterial("warrior")
            .addLoot("minecraft:chests/village/village_armorer", 1.0f)
            .addLoot("minecraft:chests/shipwreck_supply", 1.0f)
            .protectionAmount(2, 5, 6, 2)
            .durabilityMultiplier(15)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.IRON_INGOT))
            .toughness(1.0f)
            .enchantability(5)
            .hideCape()
            .effect(new BerserkArmorEffect(0.2f))
            .effect(new WeaponEfficiency(0.05f, AxeItem.class, "axe"))
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial HEAVY_ARMOR = registerSet(new ExtendedArmorMaterial("heavy")
            .addLoot("minecraft:chests/village/village_armorer", 1.0f)
            .addLoot("minecraft:chests/stronghold_crossing", 1.0f)
            .protectionAmount(4, 6, 5, 3)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.IRON_INGOT))
            .toughness(4.0f)
            .knockbackReduction(0.5f)
            .weight(0.05f)
            .enchantability(6)
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial ROBE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("robe")
            .addLoot("minecraft:chests/village/village_shepherd", 0.25f)
            .addLoot("minecraft:chests/woodland_mansion", 1.0f)
            .addLoot("minecraft:chests/igloo_chest", 1.0f)
            .protectionAmount(2, 3, 2, 1)
            .enchantability(50)
            .durabilityMultiplier(14)
            .repairIngredient(() -> Ingredient.fromTag(ItemTags.WOOL))
            .color(11546150)
            .effect(new FireResistanceArmorEffect(0.25f))
            .effect(new FireInflictingArmorEffect(10))
            .effect(new MagicProtectionArmorEffect(0.2f))
            .equipSound(SoundEvents.BLOCK_WOOL_PLACE));

    ExtendedArmorMaterial SLIME_ARMOR = registerSet(new ExtendedArmorMaterial("slime")
            .addLoot("minecraft:chests/simple_dungeon", 0.25f)
            .protectionAmount(3, 5, 4, 2)
            .enchantability(10)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.SLIME_BALL))
            .knockbackReduction(0.25f)
            .effect(new BouncingArmorEffect(0.25f))
            .effect(new ExplosionProtectionArmorEffect(0.2f))
            .equipSound(SoundEvents.ENTITY_SLIME_SQUISH));

    ExtendedArmorMaterial DIVINE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("divine")
            .addLoot("minecraft:chests/village/village_temple", 0.4f)
            .addLoot("minecraft:chests/bastion_treasure", 0.25f)
            .addLoot("minecraft:chests/woodland_mansion", 0.25f)
            .addLoot("minecraft:chests/desert_pyramid", 1.0f)
            .addLoot("minecraft:blocks/coal_block", 0.1f)
            .protectionAmount(3, 7, 5, 3)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.GOLD_INGOT))
            .enchantability(30)
            .effect(new DivineArmorEffect(1200))
            .color(11546150)
            .hideCape()
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial PRISMARINE_ARMOR = registerSet(new ExtendedArmorMaterial("prismarine")
            .addLoot("minecraft:chests/underwater_ruin_big", 1.0f)
            .addLoot("minecraft:chests/underwater_ruin_small", 0.5f)
            .protectionAmount(3, 8, 6, 3)
            .enchantability(8)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.PRISMARINE_CRYSTALS))
            .weight(0.02f)
            .effect(new SpikesArmorEffect(1))
            .enchantment(Enchantments.DEPTH_STRIDER, 2)
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    ExtendedArmorMaterial WOODEN_ARMOR = registerSet(new ExtendedArmorMaterial("wooden")
            .addLoot("minecraft:chests/village/village_fletcher", 0.25f)
            .protectionAmount(1, 3, 2, 1)
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.fromTag(ItemTags.LOGS))
            .enchantability(4)
            .effect(new ArrowBlockArmorEffect(0.15f))
            .effect(new ExplosionProtectionArmorEffect(0.1f))
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER));

    ExtendedArmorMaterial STEAMPUNK_ARMOR = registerSet(new ExtendedArmorMaterial("steampunk")
            .addLoot("minecraft:chests/village/village_toolsmith", 0.25f)
            .addLoot("minecraft:chests/shipwreck_treasure", 1.0f)
            .protectionAmount(3, 6, 3, 2)
            .durabilityMultiplier(10)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.GOLD_INGOT))
            .enchantability(4)
            .hideCape()
            .effect(new ExplosionProtectionArmorEffect(0.1f))
            .effect(new SteamTechArmorEffect())
            .equipSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT));

    static void bootstrap() {

    }

    static ExtendedArmorMaterial registerSet(ExtendedArmorMaterial material) {
        if (Config.getInstance().enabledArmors.getOrDefault(material.getName(), true)) {
            Items.items.putAll(register(material.getName() + "_helmet", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material), material));
            Items.items.putAll(register(material.getName() + "_chestplate", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material), material));
            Items.items.putAll(register(material.getName() + "_leggings", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material), material));
            Items.items.putAll(register(material.getName() + "_boots", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material), material));
        }
        return material;
    }

    static ExtendedArmorMaterial registerDyeableSet(ExtendedArmorMaterial material) {
        if (Config.getInstance().enabledArmors.getOrDefault(material.getName(), true)) {
            Items.coloredItems.putAll(register(material.getName() + "_helmet", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material), material));
            Items.coloredItems.putAll(register(material.getName() + "_chestplate", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material), material));
            Items.coloredItems.putAll(register(material.getName() + "_leggings", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material), material));
            Items.coloredItems.putAll(register(material.getName() + "_boots", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material), material));

            Items.items.putAll(Items.coloredItems);
        }
        return material;
    }

    static Map<String, Supplier<Item>> register(String name, Supplier<Item> item, ExtendedArmorMaterial material) {
        Supplier<Item> register = Registration.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
        for (Map.Entry<String, Float> entry : material.getLoot().entrySet()) {
            lootLookup.putIfAbsent(entry.getKey(), new HashMap<>());
            lootLookup.get(entry.getKey()).put(register, entry.getValue());
        }
        return Collections.singletonMap(name, register);
    }

    static Item.Settings baseProps() {
        return new Item.Settings().group(ItemGroups.ARMOR);
    }
}
