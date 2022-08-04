package immersive_armors.armorEffects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpikesArmorEffect extends ArmorEffect {
    private final int strength;

    public SpikesArmorEffect(int strength) {
        this.strength = strength;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("armorEffect.spikes", strength).formatted(Formatting.RED));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && !source.isProjectile()) {
            Entity attacker = source.getAttacker();
            if (attacker != null) {
                attacker.damage(DamageSource.thorns(entity), strength * getSetCount(armor));
            }
        }

        return amount;
    }
}
