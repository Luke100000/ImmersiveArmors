package immersive_armors.armorDamageEffects;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ArmorEffect {
    abstract public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor);

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

    }

    public void equippedTick(ItemStack stack, World world, LivingEntity entity, int slot) {

    }
}
