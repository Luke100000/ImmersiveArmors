package immersive_armors.mixin;

import immersive_armors.Config;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "isPartVisible", at = @At("HEAD"), cancellable = true)
    public void isPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.getInstance().hideSecondLayerUnderArmor) {
            return;
        }

        EquipmentSlot slot = null;
        int index = -1;
        switch (modelPart.getName()) {
            case "jacket":
            case "left_sleeve":
            case "right_sleeve":
                slot = EquipmentSlot.CHEST;
                index = 1;
                break;
            case "left_pants_leg":
            case "right_pants_leg":
                slot = EquipmentSlot.LEGS;
                index = 2;
                break;
            case "hat":
                slot = EquipmentSlot.HEAD;
                index = 0;
                break;
        }

        if (index >= 0) {
            ItemStack stack = getEquippedStack(slot);
            if (stack.getItem() instanceof ExtendedArmorItem) {
                if (((ExtendedArmorItem)stack.getItem()).getMaterial().shouldHideSecondLayer()[index]) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
