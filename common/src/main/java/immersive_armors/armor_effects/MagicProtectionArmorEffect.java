package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class MagicProtectionArmorEffect extends ArmorEffect {
    private final float strength;

    public MagicProtectionArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (source.is(DamageTypes.MAGIC)) {
            return amount * (1.0f - strength);
        } else {
            return amount;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.magicResistance", (int)(strength * 100)).withStyle(ChatFormatting.BLUE));
    }
}
