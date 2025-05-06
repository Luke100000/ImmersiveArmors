package immersive_armors.armor_effects;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class WeaponEfficiency extends ArmorEffect {
    private final float damage;
    private final TagKey<Item> weapon;
    private final String weaponName;

    public WeaponEfficiency(float damage, Identifier weapon, String weaponName) {
        this.damage = damage;
        this.weapon = TagKey.of(RegistryKeys.ITEM, weapon);
        this.weaponName = weaponName;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        MutableText weaponText = Text.translatable("armorEffect.weaponEfficiency." + weaponName);
        tooltip.add(Text.translatable("armorEffect.weaponEfficiency", (int) (damage * 100), weaponText).formatted(Formatting.GOLD));
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.isDirect() && source.getAttacker() instanceof LivingEntity attacker) {
            if (isPrimaryArmor(armor, attacker)) {
                boolean hasAxe = Stream.of(attacker.getEquippedStack(EquipmentSlot.MAINHAND), attacker.getEquippedStack(EquipmentSlot.OFFHAND))
                        .filter(Objects::nonNull)
                        .anyMatch(v -> v.isIn(weapon));
                if (hasAxe) {
                    amount *= (1.0f + getSetCount(armor, attacker) * damage);
                }
            }
        }
        return amount;
    }
}
