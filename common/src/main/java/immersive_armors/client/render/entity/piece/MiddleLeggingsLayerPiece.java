package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class MiddleLeggingsLayerPiece extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(0.5f);

    public MiddleLeggingsLayerPiece() {
        texture("leggings_middle");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }
}
