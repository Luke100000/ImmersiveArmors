package immersive_armors.armorDamageEffects;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class FireResistanceArmorEffect extends ArmorEffect {
    private final float strength;

    public FireResistanceArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public float applyArmorToDamage(Entity entity, DamageSource source, float amount, ItemStack armor) {
        if (source.isFire()) {
            return amount * (1.0f - strength);
        } else {
            return amount;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, tooltip, context);

        tooltip.add(new TranslatableText("damageEffect.fireResistance", (int) (strength * 100)).formatted(Formatting.RED));
    }
}
