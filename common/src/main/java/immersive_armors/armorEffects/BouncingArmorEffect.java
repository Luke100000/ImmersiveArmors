package immersive_armors.armorEffects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BouncingArmorEffect extends ArmorEffect {
    private final float strength;

    public BouncingArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("armorEffect.bounceback", (int)(strength * 100)).formatted(Formatting.GREEN));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        Entity attacker = source.getAttacker();
        if (attacker != null && !source.isIndirect()) {
            Vec3d direction = attacker.getPos().subtract(entity.getPos()).normalize().multiply(strength);
            Vec3d velocity = attacker.getVelocity();
            attacker.setVelocity(velocity.add(direction));
        }
        return amount;
    }
}
