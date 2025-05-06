package immersive_armors.armor_effects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

public class BouncingArmorEffect extends ArmorEffect {
    private final float strength;

    public BouncingArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("armorEffect.bounceback", (int)(strength * 100)).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
        Entity attacker = source.getEntity();
        if (attacker != null && source.isDirect()) {
            Vec3 direction = attacker.position().subtract(entity.position()).normalize().scale(strength);
            Vec3 velocity = attacker.getDeltaMovement();
            attacker.setDeltaMovement(velocity.add(direction));
        }
        return amount;
    }
}
