package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class MiddleBodyLayerPiece  extends LayerPiece {
    private static final HumanoidModel<LivingEntity> model = buildDilatedModel(0.75f, 0.9f);

    public MiddleBodyLayerPiece() {
        texture("body_middle");
    }

    @Override
    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }
}
