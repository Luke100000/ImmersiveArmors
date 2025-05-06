package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class LowerBodyLayerPiece extends LayerPiece {
    private static final HumanoidModel<LivingEntity> model = buildDilatedModel(0.25f, 0.55f);

    public LowerBodyLayerPiece() {
        texture("body_lower");
    }

    @Override
    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }

}
