package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.client.render.entity.model.DecoModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ModelPiece extends Piece {
    private final DecoModel model;

    public ModelPiece(DecoModel model) {
        super();
        this.model = model;
    }

    @Override
    public <T extends LivingEntity, A extends HumanoidModel<T>> void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        model.copyFromModel(armorModel, armorSlot);
        renderParts(matrices, vertexConsumers, light, itemStack, (ExtendedArmorItem)itemStack.getItem(), model, 0xFFFFFFFF, false);
    }
}
