package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class UpperBodyLayerPiece extends LayerPiece {
    private static final HumanoidModel<LivingEntity> model = buildDilatedModel(1.25f, 1.25f);

    public UpperBodyLayerPiece() {
        texture("body_upper");
    }

    @Override
    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }
}
