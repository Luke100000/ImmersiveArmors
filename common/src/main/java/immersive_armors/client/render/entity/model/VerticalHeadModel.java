package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;

public class VerticalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public VerticalHeadModel() {
        part = new ModelPart(64, 16, 0, 0);
        part.addCuboid(0.0F, -22.0F, -12.0F, 0.0F, 12.0F, 20.0F, 0.0f);
        part.setPivot(0.0F, 0.0F, 0.0F);
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
