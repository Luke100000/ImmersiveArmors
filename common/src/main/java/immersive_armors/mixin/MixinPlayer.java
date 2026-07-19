package immersive_armors.mixin;

import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Avatar.class)
public abstract class MixinPlayer {
    @Inject(method = "isModelPartShown", at = @At("HEAD"), cancellable = true)
    public void immersiveArmors$injectIsPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.getInstance().hideSecondLayerUnderArmor) {
            return;
        }

        if ((Object) this instanceof Player player && player.isLocalPlayer() && !Minecraft.getInstance().gameRenderer.mainCamera().isDetached()) {
            return;
        }

        int flag = modelPart.getBit();
        EquipmentSlot slot = null;
        int index = -1;
        switch (flag) {
            case 1, 2, 3 -> {
                slot = EquipmentSlot.CHEST;
                index = 1;
            }
            case 4, 5 -> {
                slot = EquipmentSlot.LEGS;
                index = 2;
            }
            case 6 -> {
                slot = EquipmentSlot.HEAD;
                index = 0;
            }
        }

        if (index >= 0) {
            ItemStack stack = ((LivingEntity) (Object) this).getItemBySlot(slot);
            if (stack.getItem() instanceof ExtendedArmorItem armorItem && armorItem.getExtendedMaterial().shouldHideSecondLayer()[index]) {
                cir.setReturnValue(false);
            }
        }
    }
}
