package immersive_armors.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.ExtendedArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ImmersiveArmorRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        ExtendedArmorItem item = (ExtendedArmorItem) stack.getItem();
        item.getExtendedMaterial().getPieces(item.getEquipmentSlot()).forEach(piece ->
                piece.render(matrices, vertexConsumers, light, entity, stack, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false), item.getEquipmentSlot(), contextModel));
    }
}
