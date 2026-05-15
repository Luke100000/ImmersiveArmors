package immersive_armors.mixin;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinClientLivingEntity {
    @Inject(method = "tick", at = @At("TAIL"))
    private void immersiveArmors$tickClientArmorEffects(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity != Minecraft.getInstance().player) {
            return;
        }

        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.getItem() instanceof ExtendedArmorItem item) {
                item.equipmentTick(stack, entity.level(), entity, slot);
            }
        }
    }
}
