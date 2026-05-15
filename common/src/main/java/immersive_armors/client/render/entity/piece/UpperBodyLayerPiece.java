package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class UpperBodyLayerPiece extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(1.25f, 1.25f);

    public UpperBodyLayerPiece() {
        texture("body_upper");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }
}
