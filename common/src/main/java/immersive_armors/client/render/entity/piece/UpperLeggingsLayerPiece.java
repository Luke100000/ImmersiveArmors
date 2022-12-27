package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class UpperLeggingsLayerPiece extends LayerPiece {
    private static final BipedEntityModel<LivingEntity> model = buildDilatedModel(1.0f);

    public UpperLeggingsLayerPiece() {
        texture("leggings_upper");
    }

    @Override
    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }
}
