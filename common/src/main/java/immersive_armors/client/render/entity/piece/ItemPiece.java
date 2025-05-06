package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.client.render.entity.model.DecoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
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
    public <T extends LivingEntity, A extends HumanoidModel<T>> void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        matrices.pushPose();
        DecoModel.getModelPart(armorModel, attachTo).translateAndRotate(matrices);
        matrices.translate(x, y, z);
        matrices.scale(size, -size, -size);
        if (rotation != null) {
            matrices.mulPose(rotation);
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(entity, stack, ItemDisplayContext.GROUND, false, matrices, vertexConsumers, entity.level(), light, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }
}
