package immersive_armors.client.render.entity.piece;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class LowerLeggingsLayerPiece extends LayerPiece {
    private static final HumanoidModel<LivingEntity> model = buildDilatedModel(0.125f);

    public LowerLeggingsLayerPiece() {
        texture("leggings_lower");
    }

    @Override
    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }
}
