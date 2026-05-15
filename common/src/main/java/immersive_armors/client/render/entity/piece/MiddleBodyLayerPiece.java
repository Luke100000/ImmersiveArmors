package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class MiddleBodyLayerPiece  extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(0.75f, 0.9f);

    public MiddleBodyLayerPiece() {
        texture("body_middle");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }
}
