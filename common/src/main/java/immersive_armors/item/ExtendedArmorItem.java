package immersive_armors.item;

import com.google.common.base.Suppliers;
import immersive_armors.armor_effects.ArmorEffect;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class ExtendedArmorItem extends ArmorItem {
    private Supplier<ItemAttributeModifiers> attributeModifiers;
    private final ExtendedArmorMaterial material;

    public ExtendedArmorItem(Item.Properties settings, ArmorItem.Type slot, ExtendedArmorMaterial material) {
        super(material.getRegistryReference(), slot, settings);

        this.material = material;

        refreshAttributes();
    }

    @Override
    public int getDefense() {
        return material.getProtection(type);
    }

    @Override
    public float getToughness() {
        return material.getToughness();
    }

    public void refreshAttributes() {
        this.attributeModifiers = Suppliers.memoize(() -> {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation identifier = ResourceLocation.withDefaultNamespace("armor." + type.getName());

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
            float maxHealth = material.getExtraHealth();
            if (maxHealth != 0.0F) {
                builder.add(Attributes.MAX_HEALTH, new AttributeModifier(identifier, maxHealth, AttributeModifier.Operation.ADD_VALUE), slot);
            }
            float attackDamage = material.getAttackDamage();
            if (attackDamage != 0.0F) {
                builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier, attackDamage, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            }
            float attackSpeed = material.getAttackSpeed();
            if (attackSpeed != 0.0F) {
                builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(identifier, attackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            }
            float luck = material.getLuck();
            if (luck != 0.0F) {
                builder.add(Attributes.LUCK, new AttributeModifier(identifier, luck, AttributeModifier.Operation.ADD_VALUE), slot);
            }
            float waterMovement = material.getWaterMovement();
            if (waterMovement != 0.0F) {
                builder.add(Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(identifier, waterMovement, AttributeModifier.Operation.ADD_VALUE), slot);
            }

            return builder.build();
        });
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return attributeModifiers.get();
    }

    public ExtendedArmorMaterial getExtendedMaterial() {
        return material;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        //effects
        for (ArmorEffect e : getExtendedMaterial().getEffects()) {
            e.appendTooltip(stack, context, tooltip, type);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof LivingEntity livingEntity) {
            ItemStack equippedStack = livingEntity.getItemBySlot(getEquipmentSlot());
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
