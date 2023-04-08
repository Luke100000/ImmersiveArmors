package immersive_armors.armorEffects;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrowBlockArmorEffect extends ArmorEffect {
    private final float chance;

    public ArrowBlockArmorEffect(float chance) {
        this.chance = chance;
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        if (source.isIn(DamageTypeTags.IS_PROJECTILE) && isPrimaryArmor(armor, entity) && entity.world.random.nextFloat() < getSetCount(armor, entity) * chance) {
            entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, entity.getSoundCategory(), 0.5f, 1.25f);
            return 0;
        }
        return amount;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("armorEffect.arrowBlock", (int)(chance * 100)).formatted(Formatting.GOLD));
    }
}
