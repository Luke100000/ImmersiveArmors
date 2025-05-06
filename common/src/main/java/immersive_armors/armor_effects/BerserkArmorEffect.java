package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class BerserkArmorEffect extends ArmorEffect {
    private final float berserk;

    public BerserkArmorEffect(float berserk) {
        this.berserk = berserk;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.berserk", (int) (berserk * 100)).withStyle(ChatFormatting.RED));
    }

    @Override
    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        if (source.getEntity() instanceof LivingEntity attacker && isPrimaryArmor(armor, attacker)) {
            float healthFactor = attacker.getHealth() / attacker.getMaxHealth();
            amount *= (1.0f + getSetCount(armor, attacker) * berserk * (1.0f - healthFactor));
        }
        return amount;
    }
}
