package immersive_armors.client.render.entity.piece;

import immersive_armors.client.render.entity.model.DecoModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class ItemPiece extends Piece {
    private final String attachTo;
    private final float x, y, z;
    private final float size;

    private final ItemStack stack;
    private final Quaternionf rotation;

    public ItemPiece(String to, float x, float y, float z, float size, ItemStack stack) {
        this(to, x, y, z, size, stack, null);
    }

    public ItemPiece(String to, float x, float y, float z, float size, ItemStack stack, @Nullable Quaternionf rotation) {
        attachTo = to;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.stack = stack;
        this.rotation = rotation;
    }

    @Override
    public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        matrices.push();
        DecoModel.getModelPart(armorModel, attachTo).rotate(matrices);
        matrices.translate(x, y, z);
        matrices.scale(size, -size, -size);
        if (rotation != null) {
            matrices.multiply(rotation);
        }
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, entity.world, light, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }
}
