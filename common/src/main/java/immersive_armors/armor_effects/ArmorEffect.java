package immersive_armors.armor_effects;

import immersive_armors.CustomDataComponentTypes;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class ArmorEffect {
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        return amount;
    }

    public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
        return amount;
    }

    private final List<EquipmentSlot> armorEquipmentSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ExtendedArmorMaterial material) {
        return armorEquipmentSlots.stream()
                .map(entity::getItemBySlot)
                .filter(Objects::nonNull)
                .filter(stack -> stack.getItem() instanceof ExtendedArmorItem && ((ExtendedArmorItem)stack.getItem()).getExtendedMaterial() == material);
    }

    protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ItemStack stack) {
        return getMatchingEquippedArmor(entity, ((ExtendedArmorItem)stack.getItem()).getExtendedMaterial());
    }

    protected boolean isPrimaryArmor(ItemStack stack, LivingEntity entity) {
        return stack == getMatchingEquippedArmor(entity, stack).findFirst().orElse(null);
    }

    protected int getSetCount(ItemStack stack, LivingEntity entity) {
        return (int)getMatchingEquippedArmor(entity, stack).count();
    }

    protected int getSetCount(ItemStack stack) {
        return stack.getOrDefault(CustomDataComponentTypes.SET_COUNT, 0);
    }

    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {

    }

    public void equippedTick(ItemStack stack, Level world, LivingEntity entity, int slot) {
        if (world.getGameTime() % 20 == 0) {
            stack.set(CustomDataComponentTypes.SET_COUNT, getSetCount(stack, entity));
        }
    }

    public void receiveCommand(ItemStack armor, Level world, LivingEntity entity, int slot, String command) {

    }
}
