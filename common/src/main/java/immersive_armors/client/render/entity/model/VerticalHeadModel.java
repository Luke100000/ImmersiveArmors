package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;

public class VerticalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public VerticalHeadModel() {
        this(0.0f, 0.0f, 0.0f);
    }

    public VerticalHeadModel(float x, float y, float z) {
        super();

        ModelData modelData = new ModelData();

        modelData.getRoot().addChild("part",
                ModelPartBuilder.create()
                        .cuboid(0.0F, -17.0F, -10.0F, 0.0F, 12.0F, 20.0F),
                ModelTransform.pivot(x, y, z));

        ModelPart model = TexturedModelData.of(modelData, 64, 16).createModel();
        part = model.getChild("part");
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
