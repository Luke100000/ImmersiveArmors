package immersive_armors.armor_effects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BerserkArmorEffect extends ArmorEffect {
    private final float berserk;

    public BerserkArmorEffect(float berserk) {
        this.berserk = berserk;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("armorEffect.berserk", (int) (berserk * 100)).formatted(Formatting.RED));
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.getAttacker() instanceof LivingEntity attacker && isPrimaryArmor(armor, attacker)) {
            float healthFactor = attacker.getHealth() / attacker.getMaxHealth();
            amount *= (1.0f + getSetCount(armor, attacker) * berserk * (1.0 - healthFactor));
        }
        return amount;
    }
}
