package immersive_armors.armor_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class BerserkArmorEffect extends ArmorEffect {
    private final float berserk;

    public BerserkArmorEffect(float berserk) {
        this.berserk = berserk;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("armorEffect.berserk", (int) (berserk * 100)).formatted(Formatting.RED));
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.getAttacker() instanceof LivingEntity attacker && isPrimaryArmor(armor, attacker)) {
            float healthFactor = attacker.getHealth() / attacker.getMaxHealth();
            amount *= (1.0f + getSetCount(armor, attacker) * berserk * (1.0f - healthFactor));
        }
        return amount;
    }
}
