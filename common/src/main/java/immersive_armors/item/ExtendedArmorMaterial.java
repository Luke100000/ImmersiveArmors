package immersive_armors.item;

import immersive_armors.Main;
import immersive_armors.armor_effects.ArmorEffect;
import immersive_armors.client.render.entity.piece.Piece;
import java.util.*;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class ExtendedArmorMaterial {
    private final String name;

    private int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protection = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 0);
        map.put(ArmorItem.Type.LEGGINGS, 0);
        map.put(ArmorItem.Type.CHESTPLATE, 0);
        map.put(ArmorItem.Type.HELMET, 0);
        map.put(ArmorItem.Type.BODY, 0);
    });
    private final boolean[] hidesSecondLayer = {false, false, false, false};
    private float toughness;
    private float knockbackResistance;
    private int enchantability;
    private float weight;
    private int color = 10511680;
    private float waterMovement;
    private final List<ArmorEffect> effects = new LinkedList<>();
    private final Map<String, Float> loot = new HashMap<>();

    private boolean antiSkeleton;

    private final Map<EquipmentSlot, List<Piece>> pieces = new HashMap<>();

    {
        pieces.put(EquipmentSlot.HEAD, new LinkedList<>());
        pieces.put(EquipmentSlot.CHEST, new LinkedList<>());
        pieces.put(EquipmentSlot.LEGS, new LinkedList<>());
        pieces.put(EquipmentSlot.FEET, new LinkedList<>());
    }

    private boolean hideCape;

    private Holder<SoundEvent> equipSound;
    private Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

    private Holder<ArmorMaterial> registryReference;

    public ExtendedArmorMaterial(String name) {
        this.name = name;

        protectionAmount(0, 0, 0, 0);
    }

    public ExtendedArmorMaterial durabilityMultiplier(int durabilityMultiplier) {
        this.durabilityMultiplier = durabilityMultiplier;
        return this;
    }

    public ExtendedArmorMaterial protectionAmount(int helmet, int chestplate, int legging, int boots) {
        this.protection.put(ArmorItem.Type.HELMET, helmet);
        this.protection.put(ArmorItem.Type.CHESTPLATE, chestplate);
        this.protection.put(ArmorItem.Type.LEGGINGS, legging);
        this.protection.put(ArmorItem.Type.BOOTS, boots);
        this.protection.put(ArmorItem.Type.BODY, chestplate);
        return this;
    }

    public ExtendedArmorMaterial toughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    public ExtendedArmorMaterial enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public ExtendedArmorMaterial equipSound(SoundEvent equipSound) {
        return equipSound(Holder.direct(equipSound));
    }

    public ExtendedArmorMaterial equipSound(Holder<SoundEvent> equipSound) {
        this.equipSound = equipSound;
        return this;
    }

    public ExtendedArmorMaterial repairIngredient(Supplier<Ingredient> repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    public ExtendedArmorMaterial knockbackReduction(float knockbackReduction) {
        this.knockbackResistance = knockbackReduction;
        return this;
    }

    public ExtendedArmorMaterial weight(float weight) {
        this.weight = weight;
        return this;
    }

    public ExtendedArmorMaterial color(int color) {
        this.color = color;
        return this;
    }

    public ExtendedArmorMaterial waterMovement(float waterMovement) {
        this.waterMovement = waterMovement;
        return this;
    }

    public ExtendedArmorMaterial effect(ArmorEffect effect) {
        this.effects.add(effect);
        return this;
    }

    public ExtendedArmorMaterial antiSkeleton() {
        this.antiSkeleton = true;
        return this;
    }

    public ExtendedArmorMaterial hideCape() {
        this.hideCape = true;
        return this;
    }

    public ExtendedArmorMaterial head(Piece pieceSupplier) {
        this.pieces.get(EquipmentSlot.HEAD).add(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial chest(Piece pieceSupplier) {
        this.pieces.get(EquipmentSlot.CHEST).add(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial legs(Piece pieceSupplier) {
        this.pieces.get(EquipmentSlot.LEGS).add(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial feet(Piece pieceSupplier) {
        this.pieces.get(EquipmentSlot.FEET).add(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial upper(Piece pieceSupplier) {
        head(pieceSupplier);
        chest(pieceSupplier);
        feet(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial lower(Piece pieceSupplier) {
        legs(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial full(Piece pieceSupplier) {
        upper(pieceSupplier);
        lower(pieceSupplier);
        return this;
    }

    public ExtendedArmorMaterial addLoot(String name, float chance) {
        loot.put(name, chance);
        return this;
    }

    public String getName() {
        return name;
    }


    public int getDurability(ArmorItem.Type slot) {
        return BASE_DURABILITY[slot.getSlot().getIndex()] * this.durabilityMultiplier;
    }


    public int getProtection(ArmorItem.Type slot) {
        return protection.get(slot);
    }


    public float getToughness() {
        return toughness;
    }


    public int getEnchantability() {
        return enchantability;
    }


    public Holder<SoundEvent> getEquipSound() {
        return equipSound;
    }


    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }


    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public float getWeight() {
        return weight;
    }

    public int getColor() {
        return color;
    }

    public float getWaterMovement() {
        return waterMovement;
    }

    public List<ArmorEffect> getEffects() {
        if (Main.sharedConfig.enableEffects) {
            return effects;
        } else {
            return Collections.emptyList();
        }
    }

    public boolean shouldHideCape() {
        return hideCape;
    }

    public List<Piece> getPieces(EquipmentSlot slot) {
        return pieces.get(slot);
    }

    public ExtendedArmorMaterial hidesSecondLayer(boolean head, boolean chest, boolean legs, boolean feet) {
        hidesSecondLayer[0] = head;
        hidesSecondLayer[1] = chest;
        hidesSecondLayer[2] = legs;
        hidesSecondLayer[3] = feet;
        return this;
    }

    public boolean[] shouldHideSecondLayer() {
        return hidesSecondLayer;
    }

    public boolean isAntiSkeleton() {
        return antiSkeleton;
    }

    public EnumMap<ArmorItem.Type, Integer> getProtection() {
        return protection;
    }

    public int getDurabilityMultiplier() {
        return durabilityMultiplier;
    }

    public Map<String, Float> getLoot() {
        return loot;
    }

    public ArmorMaterial getMaterial() {
        return new ArmorMaterial(protection, enchantability, equipSound, repairIngredient, List.of(), toughness, knockbackResistance);
    }

    public void registerVanillaMaterial() {
        this.registryReference = Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                Main.locate(getName()),
                getMaterial()
        );
    }

    public Holder<ArmorMaterial> getRegistryReference() {
        return registryReference;
    }
}