package immersive_armors.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.EquipmentSlot;

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

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        part.render(matrices, vertices, light, overlay, color);
    }

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        part.copyFrom(getModelPart(model, attachTo));
        super.copyFromModel(model, slot);
    }

    public String getAttachTo() {
        return attachTo;
    }

    public ModelPart getPart() {
        return part;
    }
}