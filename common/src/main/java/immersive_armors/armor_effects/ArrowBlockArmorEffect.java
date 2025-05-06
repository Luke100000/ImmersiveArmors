package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ArrowBlockArmorEffect extends ArmorEffect {
    private final float chance;

    public ArrowBlockArmorEffect(float chance) {
        this.chance = chance;
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (source.is(DamageTypeTags.IS_PROJECTILE) && isPrimaryArmor(armor, entity) && entity.level().random.nextFloat() < getSetCount(armor, entity) * chance) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK, entity.getSoundSource(), 0.5f, 1.25f);
            return 0;
        }
        return amount;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.arrowBlock", (int)(chance * 100)).withStyle(ChatFormatting.GOLD));
    }
}
