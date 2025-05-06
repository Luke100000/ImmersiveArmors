package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class UpperLeggingsLayerPiece extends LayerPiece {
    private static final HumanoidModel<LivingEntity> model = buildDilatedModel(1.0f);

    public UpperLeggingsLayerPiece() {
        texture("leggings_upper");
    }

    @Override
    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }
}
