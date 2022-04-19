package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class LowerLeggingsLayerPiece extends LayerPiece {
    private static final BipedEntityModel<LivingEntity> model = new BipedEntityModel<>(0.6f);

    public LowerLeggingsLayerPiece() {
        texture("layer_2_lower");
    }

    @Override
    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }
}
