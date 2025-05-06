package immersive_armors.fabric;

import immersive_armors.item.ExtendedArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ImmersiveArmorRenderer implements ArmorRenderer {
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        ExtendedArmorItem item = (ExtendedArmorItem) stack.getItem();
        item.getExtendedMaterial().getPieces(item.getSlotType()).forEach(piece ->
                piece.render(matrices, vertexConsumers, light, entity, stack, MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false), item.getSlotType(), contextModel));
    }
}
