package immersive_armors.armor_effects;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.stream.Stream;

public class WeaponEfficiency extends ArmorEffect {
    private final float damage;
    private final TagKey<Item> weapon;
    private final String weaponName;

    public WeaponEfficiency(float damage, ResourceLocation weapon, String weaponName) {
        this.damage = damage;
        this.weapon = TagKey.create(Registries.ITEM, weapon);
        this.weaponName = weaponName;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        MutableComponent weaponText = Component.translatable("armorEffect.weaponEfficiency." + weaponName);
        tooltip.add(Component.translatable("armorEffect.weaponEfficiency", (int) (damage * 100), weaponText).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.isDirect() && source.getEntity() instanceof LivingEntity attacker) {
            if (isPrimaryArmor(armor, attacker)) {
                boolean hasAxe = Stream.of(
                        attacker.getItemBySlot(EquipmentSlot.MAINHAND),
                        attacker.getItemBySlot(EquipmentSlot.OFFHAND)
                ).anyMatch(v -> v.is(weapon));
                if (hasAxe) {
                    amount *= (1.0f + getSetCount(armor, attacker) * damage);
                }
            }
        }
        return amount;
    }
}
