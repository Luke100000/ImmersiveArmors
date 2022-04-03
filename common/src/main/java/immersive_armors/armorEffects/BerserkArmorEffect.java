package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BerserkArmorEffect extends ArmorEffect {
    private final float berserk;

    public BerserkArmorEffect(float berserk) {
        this.berserk = berserk;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("armorEffect.berserk", (int)(berserk * 100)).formatted(Formatting.RED));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        return amount;
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.getAttacker() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity)source.getAttacker();
            if (isPrimaryArmor(armor, attacker)) {
                float healthFactor = attacker.getHealth() / attacker.getMaxHealth();
                amount *= (1.0f + getSetCount(armor, attacker) * berserk * (1.0 - healthFactor));
            }
        }
        return amount;
    }
}
