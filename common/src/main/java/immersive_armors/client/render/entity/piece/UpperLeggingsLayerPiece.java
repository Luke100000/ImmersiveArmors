package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class UpperLeggingsLayerPiece  extends LayerPiece {
    private static final BipedEntityModel<LivingEntity> model = new BipedEntityModel<>(1.5f);

    public UpperLeggingsLayerPiece() {
        texture("layer_2_upper");
    }

    @Override
    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }
}
