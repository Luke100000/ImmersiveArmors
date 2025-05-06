package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class HorizontalHeadModel extends DecoHeadModel {
    private final ModelPart part;

    public HorizontalHeadModel() {
        super();

        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot().addOrReplaceChild("part",
                CubeListBuilder.create()
                        .addBox(-10.0F, -17.0F, 0.0F, 20.0F, 12.0F, 0.0F),
                PartPose.ZERO);


        ModelPart model = LayerDefinition.create(modelData, 64, 16).bakeRoot();
        part = model.getChild("part");
    }

    @Override
    ModelPart getPart() {
        return part;
    }
}
