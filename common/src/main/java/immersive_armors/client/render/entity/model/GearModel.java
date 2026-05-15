package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import java.util.List;

public class GearModel extends DecoModel {
    private final String attachTo;
    private final ModelPart part;

    public GearModel(String to, int size) {
        super();
        attachTo = to;

        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot().addOrReplaceChild("part",
                CubeListBuilder.create()
                        .addBox(-size / 2.0f, -size / 2.0f, 0.0F, size, size, 0.0f),
                PartPose.ZERO);


        ModelPart model = LayerDefinition.create(modelData, 16, 8).bakeRoot();
        part = model.getChild("part");
    }

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        copyPart(part, getModelPart(model, attachTo));
        super.copyFromModel(model, slot);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of(part);
    }

    public String getAttachTo() {
        return attachTo;
    }

    public ModelPart getPart() {
        return part;
    }
}
