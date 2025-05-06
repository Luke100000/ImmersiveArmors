package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class SpikesArmorEffect extends ArmorEffect {
    private final int strength;

    public SpikesArmorEffect(int strength) {
        this.strength = strength;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.spikes", strength).withStyle(ChatFormatting.RED));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity) && source.isDirect() && !source.typeHolder().is(DamageTypes.THORNS)) {
            Entity attacker = source.getEntity();
            if (attacker != null) {
                attacker.hurt(entity.level().damageSources().thorns(entity), strength * getSetCount(armor));
            }
        }

        return amount;
    }
}
