package immersive_armors.fabric;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.mixin.MixinMinecraftClient;
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
        item.getMaterial().getPieces(item.getSlotType()).forEach(piece -> {
            MixinMinecraftClient client = (MixinMinecraftClient) MinecraftClient.getInstance();
            piece.render(matrices, vertexConsumers, light, entity, stack, client.getPaused() ? client.getPausedTickDelta() : MinecraftClient.getInstance().getTickDelta(), item.getSlotType(), contextModel);
        });
    }
}
