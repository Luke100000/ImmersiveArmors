package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import immersive_armors.Main;
import immersive_armors.client.render.entity.model.DecoModel;
import immersive_armors.client.render.entity.model.GearModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class GearPiece<M extends GearModel> extends Piece {
    private final M model;
    private final String texture;
    private final float x, y, z;
    private final float speed;
    private final Quaternionf rotation;

    private Identifier getTexture(ExtendedArmorItem item) {
        return Main.locate("textures/models/armor/" + item.getExtendedMaterial().getName() + "/" + texture + ".png");
    }

    public GearPiece(M model, String texture, float x, float y, float z, float speed) {
        this(model, texture, x, y, z, speed, null);
    }

    public GearPiece(M model, String texture, float x, float y, float z, float speed, @Nullable Quaternionf rotation) {
        this.model = model;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order) {
        matrices.pushPose();
        DecoModel.getModelPart(armorModel, model.getAttachTo()).translateAndRotate(matrices);
        matrices.translate(x, y, z);
        if (rotation != null) {
            matrices.mulPose(rotation);
        }
        matrices.mulPose(Axis.ZP.rotationDegrees((renderState.ageInTicks + tickDelta) * speed * 180.0f / (float)Math.PI / 20.0f));
        order = renderGeometry(matrices, submitNodeCollector, RenderTypes.armorCutoutNoCull(getTexture((ExtendedArmorItem) itemStack.getItem())), light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF, model.parts(), order);
        matrices.popPose();
        return order;
    }
}
