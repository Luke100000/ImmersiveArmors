package immersive_armors.item;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class ExtendedArmorMaterial implements ArmorMaterial {
    private final String name;

    private int durabilityMultiplier;
    private int[] protectionAmount;
    private float toughness;
    private float knockbackResistance;
    private int enchantability;

    private SoundEvent equipSound;
    private Ingredient repairIngredient;

    private final Set<ArmorLayer> layers;
    private final Set<ArmorLayer> colored;

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};

    public ExtendedArmorMaterial(String name) {
        this.name = name;

        protectionAmount(0, 0, 0, 0);

        layers = new HashSet<>();
        layers.add(ArmorLayer.MIDDLE);

        colored = new HashSet<>();
    }

    public ExtendedArmorMaterial durabilityMultiplier(int durabilityMultiplier) {
        this.durabilityMultiplier = durabilityMultiplier;
        return this;
    }

    public ExtendedArmorMaterial protectionAmount(int helmet, int chestplate, int legging, int boots) {
        this.protectionAmount = new int[] {helmet, chestplate, legging, boots};
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

    public ExtendedArmorMaterial repairIngredient(Ingredient repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    public ExtendedArmorMaterial knockbackReduction(int knockbackReduction) {
        this.knockbackResistance = knockbackReduction;
        return this;
    }

    public ExtendedArmorMaterial layer(ArmorLayer layer) {
        layers.add(layer);
        return this;
    }

    public ExtendedArmorMaterial colored(ArmorLayer layer) {
        colored.add(layer);
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
        return repairIngredient;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public boolean hasLayer(ArmorLayer layer) {
        return layers.contains(layer);
    }

    public boolean isColored(ArmorLayer layer) {
        return colored.contains(layer);
    }
}
