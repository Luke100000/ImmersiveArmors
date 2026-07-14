package immersive_armors.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.client.render.entity.ImmersiveArmorRenderState;
import immersive_armors.client.render.entity.piece.Piece;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {
    public MixinHumanoidArmorLayer(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void immersiveArmors$renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, S humanoidRenderState, CallbackInfo ci) {
        // TODO: Migrate custom armor pieces to the equipment assets system.
        if (itemStack.getItem() instanceof ExtendedArmorItem armor) {
            int order = 1;
            float tickDelta = ((ImmersiveArmorRenderState) humanoidRenderState).immersiveArmors$getTickDelta();
            HumanoidModel<HumanoidRenderState> parentModel = (HumanoidModel<HumanoidRenderState>) this.getParentModel();
            for (Piece piece : armor.getExtendedMaterial().getPieces(equipmentSlot)) {
                order = piece.render(poseStack, submitNodeCollector, light, humanoidRenderState, itemStack, tickDelta, equipmentSlot, parentModel, order);
            }
            ci.cancel();
        }
    }
}
