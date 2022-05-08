package immersive_armors.item;

import immersive_armors.armorEffects.ArmorEffect;
import immersive_armors.client.render.entity.piece.Piece;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ExtendedArmorMaterial implements ArmorMaterial {
    private final String name;

    private int durabilityMultiplier;
    private int[] protectionAmount;
    private final boolean[] hidesSecondLayer = {false, false, false, false};
    private float toughness;
    private float knockbackResistance;
    private int enchantability;
    private float weight;
    private int extraHealth;
    private int color = 10511680;
    private float attackDamage;
    private float attackSpeed;
    private int luck;
    private final List<ArmorEffect> effects = new LinkedList<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();

    private final Map<EquipmentSlot, List<Piece>> pieces = new HashMap<>();
    {
        pieces.put(EquipmentSlot.HEAD, new LinkedList<>());
        pieces.put(EquipmentSlot.CHEST, new LinkedList<>());
        pieces.put(EquipmentSlot.LEGS, new LinkedList<>());
        pieces.put(EquipmentSlot.FEET, new LinkedList<>());
    }

    private boolean hideCape;

    private SoundEvent equipSound;
    private Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};

    public ExtendedArmorMaterial(String name) {
        this.name = name;

        protectionAmount(0, 0, 0, 0);
    }

    public ExtendedArmorMaterial durabilityMultiplier(int durabilityMultiplier) {
        this.durabilityMultiplier = durabilityMultiplier;
        return this;
    }

    public ExtendedArmorMaterial protectionAmount(int helmet, int chestplate, int legging, int boots) {
        this.protectionAmount = new int[] {boots, legging, chestplate, helmet};
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

    public ExtendedArmorMaterial extraHealth(int extraHealth) {
        this.extraHealth = extraHealth;
        return this;
    }

    public ExtendedArmorMaterial color(int color) {
        this.color = color;
        return this;
    }

    public ExtendedArmorMaterial attackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public ExtendedArmorMaterial attackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    public ExtendedArmorMaterial luck(int luck) {
        this.luck = luck;
        return this;
    }

    public ExtendedArmorMaterial effect(ArmorEffect effect) {
        this.effects.add(effect);
        return this;
    }

    public ExtendedArmorMaterial enchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return protectionAmount[slot.getEntitySlotId()];
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public float getWeight() {
        return weight;
    }

    public int getExtraHealth() {
        return extraHealth;
    }

    public int getColor() {
        return color;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public int getLuck() {
        return luck;
    }

    public List<ArmorEffect> getEffects() {
        return effects;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return enchantments.containsKey(enchantment);
    }

    public int getEnchantment(Enchantment enchantment) {
        return enchantments.get(enchantment);
    }

    public boolean shouldHideCape() {
        return hideCape;
    }

    public Map<EquipmentSlot, List<Piece>> getPieces() {
        return pieces;
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
}
