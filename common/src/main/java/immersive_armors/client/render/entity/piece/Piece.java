package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ArmorPiece;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public abstract class Piece {
    public Piece() {

    }

    public abstract void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, ItemStack itemStack, float tickDelta, ArmorPiece piece, BipedEntityModel<LivingEntity> bipedEntityModel);
}
