package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class WitherArmorEffect extends ArmorEffect {
    private final float immunity;
    private final int wither;

    public WitherArmorEffect(float immunity, int wither) {
        this.immunity = immunity;
        this.wither = wither;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.wither", wither).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        int setCount = getSetCount(armor, entity);
        if (isPrimaryArmor(armor, entity) && source.getEntity() instanceof LivingEntity attacker && !attacker.fireImmune()) {
            attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, wither * setCount, 1));
        }

        if (source.is(DamageTypes.WITHER)) {
            return amount * Math.min(1.0f, 1.0f - immunity * setCount);
        } else {
            return amount;
        }
    }
}
