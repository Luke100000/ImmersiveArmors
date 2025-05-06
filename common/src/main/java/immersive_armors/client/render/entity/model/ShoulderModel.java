package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import java.util.Arrays;
import java.util.Collections;

public class ShoulderModel extends DecoModel {
    private final ModelPart left, right;

    public ShoulderModel() {
        super();

        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot()
                .addOrReplaceChild("left", CubeListBuilder.create(), PartPose.ZERO)
                .addOrReplaceChild("left",
                        CubeListBuilder.create()
                                .texOffs(0, 0)
                                .addBox(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f),
                        PartPose.offsetAndRotation(5, -1, 0, 0, 0, (float)(-Math.PI * 0.125f)));

        modelData.getRoot()
                .addOrReplaceChild("right", CubeListBuilder.create(), PartPose.ZERO)
                .addOrReplaceChild("right",
                        CubeListBuilder.create()
                                .texOffs(0, 0)
                                .addBox(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f),
                        PartPose.offsetAndRotation(-5, -1, 0, 0, (float)Math.PI, (float)(Math.PI * 0.125f)));


        ModelPart model = LayerDefinition.create(modelData, 16, 16).bakeRoot();
        left = model.getChild("left");
        right = model.getChild("right");
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return Arrays.asList(left, right);
    }

    @Override
    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        left.copyFrom(model.leftArm);
        right.copyFrom(model.rightArm);
        super.copyFromModel(model, slot);
    }
}
