package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;

public class VerticalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public VerticalHeadModel() {
        this(0.0f, 0.0f, 0.0f);
    }

    public VerticalHeadModel(float x, float y, float z) {
        super();

        part = new ModelPart(64, 16, 0, 0);
        part.addCuboid(0.0F, -17.0F, -10.0F, 0.0F, 12.0F, 20.0F, 0.0f);
        part.setPivot(x, y, z);
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
