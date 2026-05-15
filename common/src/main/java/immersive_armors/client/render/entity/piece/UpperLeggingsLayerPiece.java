package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class UpperLeggingsLayerPiece extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(1.0f);

    public UpperLeggingsLayerPiece() {
        texture("leggings_upper");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }
}
