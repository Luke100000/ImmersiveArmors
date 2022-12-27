package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class LowerBodyLayerPiece extends LayerPiece {
    private static final BipedEntityModel<LivingEntity> model = buildDilatedModel(0.25f);

    public LowerBodyLayerPiece() {
        texture("body_lower");
    }

    @Override
    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }

}
