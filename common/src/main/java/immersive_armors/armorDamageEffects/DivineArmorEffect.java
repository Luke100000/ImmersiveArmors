package immersive_armors.armorDamageEffects;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DivineArmorEffect extends ArmorEffect {
    private final long cooldown;

    public DivineArmorEffect(long cooldown) {
        this.cooldown = cooldown;
    }

    private boolean isCharged(long time, ItemStack armor) {
        NbtCompound tag = armor.getOrCreateTag();
        return !tag.contains("last_divine") || tag.getLong("last_divine") + cooldown < time;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (world != null && isCharged(world.getTime(), stack)) {
            tooltip.add(new TranslatableText("damageEffect.charged").formatted(Formatting.AQUA));
        }
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (amount >= entity.getHealth()) {
            if (isCharged(entity.world.getTime(), armor)) {
                entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 0.5f, 1.25f);
                armor.getOrCreateTag().putLong("last_divine", entity.world.getTime());
                return 0;
            }
        }
        return amount;
    }
}
