package immersive_armors.armor_effects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivineArmorEffect extends ArmorEffect {
    private static final String LAST_DIVINE = "last_divine";

    private final long cooldown;

    public DivineArmorEffect(long cooldown) {
        this.cooldown = cooldown;
    }

    private boolean isCharged(long time, ItemStack armor) {
        NbtCompound tag = armor.getOrCreateNbt();
        return (!tag.contains(LAST_DIVINE) || tag.getLong(LAST_DIVINE) + cooldown < time) && getSetCount(armor) == 4;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("item.immersive_armors.divine.description").formatted(Formatting.GRAY));

        int count = getSetCount(stack);
        if (count == 4) {
            if (world != null && isCharged(world.getTime(), stack)) {
                tooltip.add(Text.translatable("armorEffect.charged").formatted(Formatting.AQUA));
            }
        } else {
            tooltip.add(Text.translatable("immersive_armors.incomplete", count, 4));
        }
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (isPrimaryArmor(armor, entity)) {
            long time = entity.getWorld().getTime();
            boolean charged = getMatchingEquippedArmor(entity, armor).anyMatch(a -> isCharged(time, a));
            if (charged) {
                entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_ANVIL_LAND, entity.getSoundCategory(), 0.5f, 1.25f);
                getMatchingEquippedArmor(entity, armor).forEach(a -> a.getOrCreateNbt().putLong(LAST_DIVINE, time));
                return 0;
            }
        }
        return amount;
    }
}
