package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class LowerBodyLayerPiece extends LayerPiece {
    private static final BipedEntityModel<LivingEntity> model = new BipedEntityModel<>(0.1f);

    public LowerBodyLayerPiece() {
        texture("layer_1_lower");
    }

    @Override
    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }

}
