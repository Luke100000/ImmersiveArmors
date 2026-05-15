package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;

public class LowerLeggingsLayerPiece extends LayerPiece {
    private static final HumanoidModel model = buildDilatedModel(0.125f);

    public LowerLeggingsLayerPiece() {
        texture("leggings_lower");
    }

    @Override
    protected HumanoidModel getModel() {
        return model;
    }
}
