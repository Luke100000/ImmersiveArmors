package immersive_armors.armorEffects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WitherArmorEffect extends ArmorEffect {
    private final float immunity;
    private final int wither;

    public WitherArmorEffect(float immunity, int wither) {
        this.immunity = immunity;
        this.wither = wither;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("armorEffect.wither", wither).formatted(Formatting.GRAY));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && source.getAttacker() instanceof LivingEntity attacker && !attacker.isFireImmune()) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, wither * getSetCount(armor, entity)));
        }

        if (Objects.equals(source.name, "wither")) {
            return amount * (1.0f - immunity);
        } else {
            return amount;
        }
    }
}
