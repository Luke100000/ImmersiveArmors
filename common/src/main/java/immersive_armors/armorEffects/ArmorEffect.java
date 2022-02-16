package immersive_armors.armorEffects;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ArmorEffect {
    abstract public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor);

    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        return amount;
    }

    private final List<EquipmentSlot> armorEquipmentSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ExtendedArmorMaterial material) {
        return armorEquipmentSlots.stream()
                .map(entity::getEquippedStack)
                .filter(Objects::nonNull)
                .filter(stack -> stack.getItem() instanceof ExtendedArmorItem && ((ExtendedArmorItem)stack.getItem()).getMaterial() == material);
    }

    protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ItemStack stack) {
        return getMatchingEquippedArmor(entity, ((ExtendedArmorItem)stack.getItem()).getMaterial());
    }

    protected boolean isPrimaryArmor(ItemStack stack, LivingEntity entity) {
        return stack == getMatchingEquippedArmor(entity, stack).findFirst().orElse(null);
    }

    protected int getSetCount(ItemStack stack, LivingEntity entity) {
        return (int)getMatchingEquippedArmor(entity, stack).count();
    }

    protected int getSetCount(ItemStack stack) {
        return stack.getOrCreateTag().getInt("set_bonus");
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

    }

    public void equippedTick(ItemStack stack, World world, LivingEntity entity, int slot) {
        if (world.getTime() % 20 == 0) {
            stack.getOrCreateTag().putInt("set_bonus", getSetCount(stack, entity));
        }
    }
}
