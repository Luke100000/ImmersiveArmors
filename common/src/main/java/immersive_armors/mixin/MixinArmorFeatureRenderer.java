package immersive_armors.mixin;

import immersive_armors.Main;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = ArmorFeatureRenderer.class, priority = 700)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$injectRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        // Prevent the armor from being rendered
        if (Main.FORGE) {
            ItemStack itemStack = entity.getEquippedStack(armorSlot);
            if (itemStack.getItem() instanceof ExtendedArmorItem) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void immersiveArmors$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float tickDelta, float j, float k, float l, CallbackInfo ci) {
        // Forge removed renderArmorParts calls
        if (Main.FORGE) {
            renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.HEAD);
            renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.CHEST);
            renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.LEGS);
            renderPieces(matrixStack, vertexConsumerProvider, i, entity, tickDelta, EquipmentSlot.FEET);
        }
    }

    private void renderPieces(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float tickDelta, EquipmentSlot armorSlot) {
        if (entity != null) {
            ItemStack equippedStack = entity.getEquippedStack(armorSlot);
            if (equippedStack.getItem() instanceof ExtendedArmorItem item) {
                item.getMaterial().getPieces(item.getSlotType()).forEach(piece -> piece.render(matrices, vertexConsumers, light, entity, equippedStack, tickDelta, item.getSlotType(), this.getContextModel()));
            }
        }
    }
}
