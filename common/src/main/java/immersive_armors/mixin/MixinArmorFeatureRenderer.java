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
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


@Mixin(value = ArmorFeatureRenderer.class, priority = 700)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    @Shadow
    protected abstract A getArmor(EquipmentSlot slot);

    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Unique
    float immersiveArmors$tickDelta;

    @Unique
    @Nullable
    ItemStack immersiveArmors$equippedStack;

    @Unique
    @Nullable
    T immersiveArmors$entity;

    @ModifyVariable(method = "renderArmor", at = @At("STORE"), ordinal = 0)
    ItemStack immersiveArmors$immersiveArmors$fetchItemStack(ItemStack itemStack) {
        this.immersiveArmors$equippedStack = itemStack;
        return itemStack;
    }

    @Inject(method = "renderArmorParts", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$injectRenderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean usesSecondLayer, A model, boolean legs, float red, float green, float blue, String overlay, CallbackInfo ci) {
        if (!Main.FORGE && immersiveArmors$equippedStack != null && immersiveArmors$equippedStack.getItem() == item && item instanceof ExtendedArmorItem armorItem) {
            renderPieces(matrices, vertexConsumers, light, armorItem);
            ci.cancel();
        }
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    void immersiveArmors$injectRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        if (Main.FORGE) {
            ItemStack itemStack = entity.getEquippedStack(armorSlot);
            if (itemStack.getItem() instanceof ExtendedArmorItem) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    public void immersiveArmors$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float tickDelta, float j, float k, float l, CallbackInfo ci) {
        this.immersiveArmors$tickDelta = tickDelta;
        this.immersiveArmors$entity = entity;

        // Forge removed renderArmorParts calls
        if (Main.FORGE) {
            renderPieces(matrixStack, vertexConsumerProvider, i, EquipmentSlot.HEAD);
            renderPieces(matrixStack, vertexConsumerProvider, i, EquipmentSlot.CHEST);
            renderPieces(matrixStack, vertexConsumerProvider, i, EquipmentSlot.LEGS);
            renderPieces(matrixStack, vertexConsumerProvider, i, EquipmentSlot.FEET);
        }
    }

    private void renderPieces(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EquipmentSlot armorSlot) {
        if (immersiveArmors$entity != null) {
            immersiveArmors$equippedStack = immersiveArmors$entity.getEquippedStack(armorSlot);
            if (immersiveArmors$equippedStack.getItem() instanceof ExtendedArmorItem armorItem) {
                renderPieces(matrices, vertexConsumers, light, armorItem);
            }
        }
    }

    private void renderPieces(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ExtendedArmorItem item) {
        if (immersiveArmors$entity != null && immersiveArmors$equippedStack != null) {
            item.getMaterial().getPieces(item.getSlotType()).forEach(piece -> {
                A armorModel = getArmor(item.getSlotType());
                this.getContextModel().setAttributes(armorModel);
                piece.render(matrices, vertexConsumers, light, immersiveArmors$entity, immersiveArmors$equippedStack, immersiveArmors$tickDelta, item.getSlotType(), armorModel);
            });
        }
    }
}
