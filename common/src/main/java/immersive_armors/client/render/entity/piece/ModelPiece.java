package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.client.render.entity.model.DecoModel;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ModelPiece extends Piece {
    private final DecoModel model;

    public ModelPiece(DecoModel model) {
        super();
        this.model = model;
    }

    @Override
    public int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order) {
        model.copyFromModel(armorModel, armorSlot);
        return renderParts(matrices, submitNodeCollector, light, renderState, itemStack, (ExtendedArmorItem)itemStack.getItem(), model.parts(), 0xFFFFFFFF, false, order);
    }
}
