package immersive_armors.mixin;

import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.MinecraftClient;
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

    @Shadow
    public abstract boolean isMainPlayer();

    @Inject(method = "isPartVisible", at = @At("HEAD"), cancellable = true)
    public void immersiveArmors$injectIsPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.getInstance().hideSecondLayerUnderArmor) {
            return;
        }

        if (isMainPlayer() && !MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson()) {
            return;
        }

        int flag = modelPart.getId();
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
            ItemStack stack = getEquippedStack(slot);
            if (stack.getItem() instanceof ExtendedArmorItem armorItem && armorItem.getMaterial().shouldHideSecondLayer()[index]) {
                cir.setReturnValue(false);
            }
        }
    }
}
