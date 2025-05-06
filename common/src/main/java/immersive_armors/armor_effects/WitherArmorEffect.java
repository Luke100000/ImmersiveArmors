package immersive_armors.armor_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class WitherArmorEffect extends ArmorEffect {
    private final float immunity;
    private final int wither;

    public WitherArmorEffect(float immunity, int wither) {
        this.immunity = immunity;
        this.wither = wither;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("armorEffect.wither", wither).formatted(Formatting.GRAY));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && source.getAttacker() instanceof LivingEntity attacker && !attacker.isFireImmune()) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, wither * getSetCount(armor, entity)));
        }

        if (source.isOf(DamageTypes.WITHER)) {
            return amount * (1.0f - immunity);
        } else {
            return amount;
        }
    }
}
