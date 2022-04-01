package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;

public class HorizontalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public HorizontalHeadModel() {
        super();
        part = new ModelPart(64, 16, 0, 0);
        part.addCuboid(-10.0F, -17.0F, 0.0F, 20.0F, 12.0F, 0.0F, 0.0f);
        part.setPivot(0.0F, 0.0F, 0.0F);
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
