package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ArmorPiece;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ItemPiece extends Piece {
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, ItemStack itemStack, float tickDelta, ArmorPiece piece) {
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity, itemStack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, entity.world, light, OverlayTexture.DEFAULT_UV);
    }
}
