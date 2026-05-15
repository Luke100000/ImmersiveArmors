package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.client.render.entity.ImmersiveArmorRenderState;
import immersive_armors.client.render.entity.model.DecoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
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
    public int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order) {
        matrices.pushPose();
        DecoModel.getModelPart(armorModel, attachTo).translateAndRotate(matrices);
        matrices.translate(x, y, z);
        matrices.scale(size, -size, -size);
        if (rotation != null) {
            matrices.mulPose(rotation);
        }

        LivingEntity entity = ((ImmersiveArmorRenderState) renderState).immersiveArmors$getEntity();
        if (entity != null) {
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(entity, stack, ItemDisplayContext.GROUND, matrices, submitNodeCollector, light);
        } else {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            Minecraft client = Minecraft.getInstance();
            client.getItemModelResolver().updateForTopItem(itemStackRenderState, stack, ItemDisplayContext.GROUND, client.level, null, ItemDisplayContext.GROUND.ordinal());
            itemStackRenderState.submit(matrices, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
        }

        matrices.popPose();
        return order + 1;
    }
}
