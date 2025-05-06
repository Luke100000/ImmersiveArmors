package immersive_armors.item;

import com.google.common.base.Suppliers;
import immersive_armors.armor_effects.ArmorEffect;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class ExtendedArmorItem extends ArmorItem {
    private Supplier<AttributeModifiersComponent> attributeModifiers;
    private final ExtendedArmorMaterial material;

    public ExtendedArmorItem(Item.Settings settings, ArmorItem.Type slot, ExtendedArmorMaterial material) {
        super(material.getRegistryReference(), slot, settings);

        this.material = material;

        refreshAttributes();
    }

    @Override
    public int getProtection() {
        return material.getProtection(type);
    }

    @Override
    public float getToughness() {
        return material.getToughness();
    }

    public void refreshAttributes() {
        this.attributeModifiers = Suppliers.memoize(() -> {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot slot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
            Identifier identifier = Identifier.ofVanilla("armor." + type.getName());

            builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, material.getProtection(type), EntityAttributeModifier.Operation.ADD_VALUE), slot);
            builder.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, material.getToughness(), EntityAttributeModifier.Operation.ADD_VALUE), slot);
            float knockbackResistance = material.getKnockbackResistance();
            if (knockbackResistance > 0.0F) {
                builder.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE), slot);
            }
            float movementSpeed = -material.getWeight();
            if (movementSpeed != 0.0F) {
                builder.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(identifier, movementSpeed, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            }
            float maxHealth = material.getExtraHealth();
            if (maxHealth != 0.0F) {
                builder.add(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(identifier, maxHealth, EntityAttributeModifier.Operation.ADD_VALUE), slot);
            }
            float attackDamage = material.getAttackDamage();
            if (attackDamage != 0.0F) {
                builder.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(identifier, attackDamage, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            }
            float attackSpeed = material.getAttackSpeed();
            if (attackSpeed != 0.0F) {
                builder.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(identifier, attackSpeed, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            }
            float luck = material.getLuck();
            if (luck != 0.0F) {
                builder.add(EntityAttributes.GENERIC_LUCK, new EntityAttributeModifier(identifier, luck, EntityAttributeModifier.Operation.ADD_VALUE), slot);
            }
            float waterMovement = material.getWaterMovement();
            if (waterMovement != 0.0F) {
                builder.add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, new EntityAttributeModifier(identifier, waterMovement, EntityAttributeModifier.Operation.ADD_VALUE), slot);
            }

            return builder.build();
        });
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        return attributeModifiers.get();
    }

    public ExtendedArmorMaterial getExtendedMaterial() {
        return material;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        //effects
        for (ArmorEffect e : getExtendedMaterial().getEffects()) {
            e.appendTooltip(stack, context, tooltip, type);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof LivingEntity livingEntity) {
            ItemStack equippedStack = livingEntity.getEquippedStack(getSlotType());
            if (equippedStack == stack) {
                for (ArmorEffect e : getExtendedMaterial().getEffects()) {
                    e.equippedTick(stack, world, livingEntity, slot);
                }
            }
        }
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
