package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class LowerBodyLayerPiece extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(0.25f, 0.55f);

    public LowerBodyLayerPiece() {
        texture("body_lower");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }

}
