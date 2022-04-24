package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;

public class HorizontalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public HorizontalHeadModel() {
        super();

        ModelData modelData = new ModelData();

        modelData.getRoot().addChild("part",
                ModelPartBuilder.create()
                        .cuboid(-10.0F, -17.0F, 0.0F, 20.0F, 12.0F, 0.0F),
                ModelTransform.NONE);


        ModelPart model = TexturedModelData.of(modelData, 64, 16).createModel();
        part = model.getChild("part");
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
