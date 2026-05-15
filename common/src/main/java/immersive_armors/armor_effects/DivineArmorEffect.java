package immersive_armors.armor_effects;

import immersive_armors.CustomDataComponentTypes;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DivineArmorEffect extends ArmorEffect {
    private final long cooldown;
    private long lastTime = 0;

    public DivineArmorEffect(long cooldown) {
        this.cooldown = cooldown;
    }

    private boolean isCharged(long time, ItemStack armor) {
        Long l = armor.get(CustomDataComponentTypes.LAST_DIVINE.get());
        return (l == null || l + cooldown < time) && getSetCount(armor) == 4;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("item.immersive_armors.divine.description").withStyle(ChatFormatting.GRAY));

        int count = getSetCount(stack);
        if (count == 4) {
            if (isCharged(this.lastTime, stack)) {
                tooltip.add(Component.translatable("armorEffect.charged").withStyle(ChatFormatting.AQUA));
            }
        } else {
            tooltip.add(Component.translatable("immersive_armors.incomplete", count, 4));
        }
    }

    @Override
    public void equippedTick(ItemStack stack, Level world, LivingEntity entity, int slot) {
        super.equippedTick(stack, world, entity, slot);

        this.lastTime = world.getGameTime();
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity)) {
            long time = entity.level().getGameTime();
            boolean charged = getMatchingEquippedArmor(entity, armor).anyMatch(a -> isCharged(time, a));
            if (charged) {
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ANVIL_LAND, entity.getSoundSource(), 0.5f, 1.25f);
                getMatchingEquippedArmor(entity, armor).forEach(a -> a.set(CustomDataComponentTypes.LAST_DIVINE.get(), time));
                return 0;
            }
        }
        return amount;
    }
}
