package immersive_armors.item;

import com.google.common.base.Suppliers;
import immersive_armors.armor_effects.ArmorEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

public class ExtendedArmorItem extends Item {
    private Supplier<ItemAttributeModifiers> attributeModifiers;
    private final ExtendedArmorMaterial material;
    private final ArmorType type;

    public ExtendedArmorItem(Item.Properties settings, ArmorType slot, ExtendedArmorMaterial material) {
        super(createSettings(settings, slot, material));

        this.material = material;
        this.type = slot;

        refreshAttributes();
    }

    private static Item.Properties createSettings(Item.Properties settings, ArmorType slot, ExtendedArmorMaterial material) {
        settings.durability(slot.getDurability(material.getDurabilityMultiplier()))
                .attributes(createAttributes(material, slot))
                .component(DataComponents.EQUIPPABLE, Equippable.builder(slot.getSlot())
                        .setEquipSound(material.getEquipSound())
                        .setAsset(material.getMaterial().assetId())
                        .build())
                .repairable(material.getMaterial().repairIngredient());

        if (material.getEnchantability() > 0) {
            settings.enchantable(material.getEnchantability());
        }

        return settings;
    }

    public int getDefense() {
        return material.getProtection(type);
    }

    public float getToughness() {
        return material.getToughness();
    }

    private static ItemAttributeModifiers createAttributes(ExtendedArmorMaterial material, ArmorType type) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
        Identifier identifier = Identifier.withDefaultNamespace("armor." + type.getName());

        builder.add(Attributes.ARMOR, new AttributeModifier(identifier, material.getProtection(type), AttributeModifier.Operation.ADD_VALUE), slot);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(identifier, material.getToughness(), AttributeModifier.Operation.ADD_VALUE), slot);
        float knockbackResistance = material.getKnockbackResistance();
        if (knockbackResistance > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(identifier, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), slot);
        }
        float movementSpeed = -material.getWeight();
        if (movementSpeed != 0.0F) {
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(identifier, movementSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
        }
        float waterMovement = material.getWaterMovement();
        if (waterMovement != 0.0F) {
            builder.add(Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(identifier, waterMovement, AttributeModifier.Operation.ADD_VALUE), slot);
        }

        return builder.build();
    }

    public void refreshAttributes() {
        this.attributeModifiers = Suppliers.memoize(() -> createAttributes(material, type));
    }

    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        return attributeModifiers.get();
    }

    public ExtendedArmorMaterial getExtendedMaterial() {
        return material;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);

        List<Component> lines = new ArrayList<>();
        for (ArmorEffect e : getExtendedMaterial().getEffects()) {
            e.appendTooltip(stack, context, lines, type);
        }
        lines.forEach(tooltip);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);

        if (entity instanceof LivingEntity livingEntity) {
            equipmentTick(stack, world, livingEntity, slot);
        }
    }

    public void equipmentTick(ItemStack stack, Level world, LivingEntity entity, EquipmentSlot slot) {
        ItemStack equippedStack = entity.getItemBySlot(getEquipmentSlot());
        if (equippedStack == stack) {
            for (ArmorEffect e : getExtendedMaterial().getEffects()) {
                e.equippedTick(stack, world, entity, slot.ordinal());
            }
        }
    }

    public EquipmentSlot getEquipmentSlot() {
        return type.getSlot();
    }

    public ArmorType getType() {
        return type;
    }

    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        for (ArmorEffect e : getExtendedMaterial().getEffects()) {
            amount = e.applyArmorToDamage(entity, source, amount, armor);
        }
        return amount;
    }

    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        for (ArmorEffect e : getExtendedMaterial().getEffects()) {
            amount = e.applyArmorToAttack(target, source, amount, armor);
        }
        return amount;
    }
}
