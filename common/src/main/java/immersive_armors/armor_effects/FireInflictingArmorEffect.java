package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FireInflictingArmorEffect extends ArmorEffect {
    private final int length;

    public FireInflictingArmorEffect(int length) {
        this.length = length;
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && source.getEntity() != null && !source.getEntity().fireImmune()) {
            source.getEntity().setRemainingFireTicks(source.getEntity().getRemainingFireTicks() + length * getSetCount(armor, entity));

            entity.level().playSound(null, entity, SoundEvents.BLAZE_BURN, entity.getSoundSource(), 1.0f, entity.getRandom().nextFloat() * 0.7F + 0.3F);
        }
        return amount;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.fireInflicting", length).withStyle(ChatFormatting.RED));
    }

    @Override
    public void equippedTick(ItemStack stack, Level world, LivingEntity entity, int slot) {
        if (world.isClientSide() && Minecraft.getInstance().player == entity && !Minecraft.getInstance().options.getCameraType().isFirstPerson() && entity.getRandom().nextInt(15) == 0) {
            world.addParticle(ParticleTypes.FLAME, entity.getRandomX(0.5D), entity.getRandomY(), entity.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
    }
}
