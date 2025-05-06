package immersive_armors.armor_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class SpikesArmorEffect extends ArmorEffect {
    private final int strength;

    public SpikesArmorEffect(int strength) {
        this.strength = strength;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("armorEffect.spikes", strength).formatted(Formatting.RED));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && source.isDirect() && !source.getTypeRegistryEntry().matchesKey(DamageTypes.THORNS)) {
            Entity attacker = source.getAttacker();
            if (attacker != null) {
                attacker.damage(entity.getWorld().getDamageSources().thorns(entity), strength * getSetCount(armor));
            }
        }

        return amount;
    }
}
