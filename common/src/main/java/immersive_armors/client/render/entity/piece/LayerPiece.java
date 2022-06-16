package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;

public abstract class LayerPiece extends Piece {
    protected abstract BipedEntityModel<LivingEntity> getModel();

    protected static BipedEntityModel<LivingEntity> buildDilatedModel(float dilation) {
        return new BipedEntityModel<>(TexturedModelData.of(BipedEntityModel.getModelData(new Dilation(dilation), 0.0f), 64, 32).createModel());
    }

    public LayerPiece() {
    }

    @Override
    public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armorItem) {
            //noinspection unchecked
            armorModel.setAttributes((BipedEntityModel<T>)getModel());
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
