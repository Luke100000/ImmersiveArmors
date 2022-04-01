package immersive_armors;

import immersive_armors.armorEffects.*;
import immersive_armors.client.render.entity.piece.CapePiece;
import immersive_armors.client.render.entity.model.*;
import immersive_armors.cobalt.registration.Registration;
import immersive_armors.item.ArmorPiece;
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

import java.util.LinkedList;
import java.util.List;

public interface Items {
    List<Item[]> coloredItems = new LinkedList<>();

    Item[] BONE_ARMOR = registerSet(new ExtendedArmorMaterial("bone")
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.BONE))
            .protectionAmount(1, 3, 2, 1)
            .enchantability(15)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .equipSound(SoundEvents.ENTITY_SKELETON_AMBIENT)
            .weight(-0.02f));

    Item[] WITHER_ARMOR = registerSet(new ExtendedArmorMaterial("wither")
            .durabilityMultiplier(12)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.BONE))
            .protectionAmount(2, 6, 5, 2)
            .enchantability(0)
            .effect(new WitherArmorEffect(1.0f, 10))
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece().chest().renderer(() -> new CapePiece<>(new CapeModel<>())))
            .hideCape()
            .equipSound(SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT)
            .weight(-0.01f));

    Item[] WARRIOR_ARMOR = registerSet(new ExtendedArmorMaterial("warrior")
            .protectionAmount(2, 5, 6, 2)
            .durabilityMultiplier(15)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.IRON_INGOT))
            .toughness(1.0f)
            .enchantability(5)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_LOWER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_LOWER).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper())
            .piece(new ArmorPiece(HorizontalHeadModel::new).head().texture("horizontal"))
            .piece(new ArmorPiece().chest().renderer(() -> new CapePiece<>(new CapeModel<>())))
            .hideCape()
            .effect(new BeserkArmorEffect(0.2f))
            .effect(new WeaponEfficiency(0.05f, AxeItem.class))
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    Item[] HEAVY_ARMOR = registerSet(new ExtendedArmorMaterial("heavy")
            .protectionAmount(4, 6, 5, 3)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.IRON_INGOT))
            .toughness(4.0f)
            .knockbackReduction(0.5f)
            .weight(0.05f)
            .enchantability(6)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_LOWER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_LOWER).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper())
            .piece(new ArmorPiece(VerticalHeadModel::new).head().texture("vertical"))
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    Item[] ROBE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("robe")
            .protectionAmount(2, 3, 2, 1)
            .enchantability(50)
            .durabilityMultiplier(14)
            .repairIngredient(() -> Ingredient.fromTag(ItemTags.WOOL))
            .color(11546150)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_LOWER).lower().colored())
            .piece(new ArmorPiece(ArmorPiece.BODY_LOWER).upper().colored())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower().colored())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper().colored())
            .effect(new FireResistanceArmorEffect(0.25f))
            .effect(new FireInflictingArmorEffect(10))
            .equipSound(SoundEvents.BLOCK_WOOL_PLACE));

    Item[] SLIME_ARMOR = registerSet(new ExtendedArmorMaterial("slime")
            .protectionAmount(3, 5, 4, 2)
            .enchantability(10)
            .durabilityMultiplier(20)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.SLIME_BALL))
            .knockbackReduction(1.0f)
            .effect(new BouncingArmorEffect(0.25f))
            .effect(new ExplosionProtectionArmorEffect(0.15f))
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_LOWER).lower().translucent())
            .piece(new ArmorPiece(ArmorPiece.BODY_LOWER).upper().translucent())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower().translucent())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper().translucent())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower().translucent())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper().translucent())
            .equipSound(SoundEvents.ENTITY_SLIME_SQUISH));

    Item[] DIVINE_ARMOR = registerDyeableSet(new ExtendedArmorMaterial("divine")
            .protectionAmount(3, 7, 5, 3)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.GOLD_INGOT))
            .enchantability(30)
            .effect(new DivineArmorEffect(1200))
            .color(11546150)
            //.glint(ArmorLayer.MIDDLE)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_LOWER).lower().colored())
            .piece(new ArmorPiece(ArmorPiece.BODY_LOWER).upper().colored())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower().colored())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper().colored())
            .piece(new ArmorPiece().chest().renderer(() -> new CapePiece<>(new CapeModel<>())).colored())
            .hideCape()
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    Item[] PRISMARINE_ARMOR = registerSet(new ExtendedArmorMaterial("prismarine")
            .protectionAmount(3, 8, 6, 3)
            .enchantability(8)
            .durabilityMultiplier(18)
            .repairIngredient(() -> Ingredient.ofItems(net.minecraft.item.Items.PRISMARINE_CRYSTALS))
            .weight(0.02f)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper())
            .piece(new ArmorPiece(PrismarineModel::new).full().texture("prismarine"))
            .effect(new SpikesArmorEffect(1))
            .enchantment(Enchantments.DEPTH_STRIDER, 1)
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON));

    Item[] WOODEN_ARMOR = registerSet(new ExtendedArmorMaterial("wooden")
            .protectionAmount(1, 3, 2, 1)
            .durabilityMultiplier(8)
            .repairIngredient(() -> Ingredient.fromTag(ItemTags.LOGS))
            .enchantability(4)
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_MIDDLE).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_MIDDLE).upper())
            .piece(new ArmorPiece(ArmorPiece.LEGGINGS_UPPER).lower())
            .piece(new ArmorPiece(ArmorPiece.BODY_UPPER).upper())
            .piece(new ArmorPiece(ShoulderModel::new).chest().texture("shoulder"))
            .effect(new ArrowBlockArmorEffect(0.15f))
            .effect(new ExplosionProtectionArmorEffect(0.1f))
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER));

    static void bootstrap() {

    }

    static Item[] registerSet(ExtendedArmorMaterial material) {
        return new Item[] {
                register(material.getName() + "_helmet", new ExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material)),
                register(material.getName() + "_chestplate", new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material)),
                register(material.getName() + "_leggings", new ExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material)),
                register(material.getName() + "_boots", new ExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material)),
        };
    }

    static Item[] registerDyeableSet(ExtendedArmorMaterial material) {
        Item[] items = {
                register(material.getName() + "_helmet", new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material)),
                register(material.getName() + "_chestplate", new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material)),
                register(material.getName() + "_leggings", new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material)),
                register(material.getName() + "_boots", new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material)),
        };
        coloredItems.add(items);
        return items;
    }

    static Item register(String name, Item item) {
        return Registration.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    static Item.Settings baseProps() {
        return new Item.Settings().group(ItemGroups.ARMOR);
    }
}
