package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;

public abstract class LayerPiece extends Piece {
    protected abstract BipedEntityModel<LivingEntity> getModel();

    public LayerPiece() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, BipedEntityModel<LivingEntity> contextModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem armorItem = (ExtendedArmorItem)itemStack.getItem();
            contextModel.setAttributes(getModel());
            setVisible(getModel(), armorSlot);

            if (isColored()) {
                int i = ((DyeableItem)armorItem).getColor(itemStack);
                float red = (float)(i >> 16 & 255) / 255.0F;
                float green = (float)(i >> 8 & 255) / 255.0F;
                float blue = (float)(i & 255) / 255.0F;
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), red, green, blue, false);
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 1.0F, 1.0F, 1.0F, true);
            } else {
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 1.0F, 1.0F, 1.0F, false);
            }
        }
    }
}
