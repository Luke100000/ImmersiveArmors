package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;

public class GearModel extends DecoModel {
    private final String attachTo;
    private final ModelPart part;

    public GearModel(String to, int size) {
        super();
        attachTo = to;

        ModelData modelData = new ModelData();

        modelData.getRoot().addChild("part",
                ModelPartBuilder.create()
                        .cuboid(-size / 2.0f, -size / 2.0f, 0.0F, size, size, 0.0f),
                ModelTransform.NONE);


        ModelPart model = TexturedModelData.of(modelData, 16, 8).createModel();
        part = model.getChild("part");
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        part.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        part.copyTransform(getModelPart(model, attachTo));
        super.copyFromModel(model, slot);
    }

    public String getAttachTo() {
        return attachTo;
    }

    public ModelPart getPart() {
        return part;
    }
}