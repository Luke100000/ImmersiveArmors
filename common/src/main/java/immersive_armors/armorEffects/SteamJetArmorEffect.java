package immersive_armors.armorEffects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SteamJetArmorEffect extends ArmorEffect {
    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText("armorEffect.steamJet").formatted(Formatting.GRAY));
    }

    @Override
    public void equippedTick(ItemStack stack, World world, LivingEntity entity, int slot) {
        super.equippedTick(stack, world, entity, slot);

        //machine smoke
        if (entity.getRandom().nextInt(10) == 0) {
            double x = Math.cos(entity.bodyYaw / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
            double z = Math.sin(entity.bodyYaw / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
            world.addParticle(ParticleTypes.SMOKE, entity.getX() + x, entity.getY() + 1.2f, entity.getZ() + z, x * 0.2f, -0.025f, z * 0.2f);
        }

        //breaking
        Vec3d velocity = entity.getVelocity();
        if (velocity.getY() < -0.75) {
            entity.setVelocity(velocity.x, -0.75f, velocity.z);

            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, entity.getParticleX(0.5D), entity.getY(), entity.getParticleZ(0.5D), 0.0D, -0.5f, 0.0D);

            if (entity.age % 6 == 0) {
                entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, entity.getSoundCategory(), 0.35f, 1.0f);
            }
        }
    }
}
