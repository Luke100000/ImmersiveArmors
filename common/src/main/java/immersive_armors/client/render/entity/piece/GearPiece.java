package immersive_armors.client.render.entity.piece;

import immersive_armors.client.render.entity.model.DecoModel;
import immersive_armors.client.render.entity.model.GearModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class GearPiece<M extends GearModel> extends Piece {
    private final M model;
    private final String texture;
    private final float x, y, z;
    private final float speed;
    private final Quaternion rotation;

    private Identifier getTexture(ExtendedArmorItem item) {
        return new Identifier("immersive_armors", "textures/models/armor/" + item.getMaterial().getName() + "/" + texture + ".png");
    }

    public GearPiece(M model, String texture, float x, float y, float z, float speed) {
        this(model, texture, x, y, z, speed, null);
    }

    public GearPiece(M model, String texture, float x, float y, float z, float speed, @Nullable Quaternion rotation) {
        this.model = model;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        matrices.push();
        DecoModel.getModelPart(armorModel, model.getAttachTo()).rotate(matrices);
        matrices.translate(x, y, z);
        if (rotation != null) {
            matrices.multiply(rotation);
        }
        matrices.multiply(new Quaternion(new Vec3f(0.0f, 0.0f, 1.0f), (entity.age + tickDelta) * speed, false));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getTexture((ExtendedArmorItem)itemStack.getItem())));
        model.getPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
    }
}
