package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import java.util.List;

public class RightVerticalShoulderModel extends DecoModel {
    private final ModelPart part;

    public RightVerticalShoulderModel() {
        super();

        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot().addOrReplaceChild("part",
                CubeListBuilder.create()
                        .addBox(-5.0f, -4f, -4f, 1.0f, 8.0f, 8.0f)
                        .addBox(-2.5f, -4f, -4f, 1.0f, 8.0f, 8.0f)
                        .addBox(0.0f, -4f, -4f, 1.0f, 8.0f, 8.0f),
                PartPose.ZERO);


        ModelPart model = LayerDefinition.create(modelData, 32, 16).bakeRoot();
        part = model.getChild("part");
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of(part);
    }

    @Override
    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        copyPart(part, model.rightArm);
        super.copyFromModel(model, slot);
    }
}
