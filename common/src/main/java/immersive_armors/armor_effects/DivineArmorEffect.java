package immersive_armors.armor_effects;

import immersive_armors.CustomDataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DivineArmorEffect extends ArmorEffect {
    private final long cooldown;
    private long lastTime = 0;

    public DivineArmorEffect(long cooldown) {
        this.cooldown = cooldown;
    }

    private boolean isCharged(long time, ItemStack armor) {
        Long l = armor.get(CustomDataComponentTypes.LAST_DIVINE);
        return (l == null || l + cooldown < time) && getSetCount(armor) == 4;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.immersive_armors.divine.description").formatted(Formatting.GRAY));

        int count = getSetCount(stack);
        if (count == 4) {
            if (isCharged(this.lastTime, stack)) {
                tooltip.add(Text.translatable("armorEffect.charged").formatted(Formatting.AQUA));
            }
        } else {
            tooltip.add(Text.translatable("immersive_armors.incomplete", count, 4));
        }
    }

    @Override
    public void equippedTick(ItemStack stack, World world, LivingEntity entity, int slot) {
        super.equippedTick(stack, world, entity, slot);

        this.lastTime = world.getTime();
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity)) {
            long time = entity.getWorld().getTime();
            boolean charged = getMatchingEquippedArmor(entity, armor).anyMatch(a -> isCharged(time, a));
            if (charged) {
                entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_ANVIL_LAND, entity.getSoundCategory(), 0.5f, 1.25f);
                getMatchingEquippedArmor(entity, armor).forEach(a -> a.set(CustomDataComponentTypes.LAST_DIVINE, time));
                return 0;
            }
        }
        return amount;
    }
}
