package immersive_armors.armor_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FireResistanceArmorEffect extends ArmorEffect {
    private final float strength;

    public FireResistanceArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (source.isIn(DamageTypeTags.IS_FIRE)) {
            return amount * (1.0f - strength);
        } else {
            return amount;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("armorEffect.fireResistance", (int)(strength * 100)).formatted(Formatting.RED));
    }
}
