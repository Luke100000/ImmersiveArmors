package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.Arrays;
import java.util.Collections;

public class ShoulderModel extends DecoModel {
    private final ModelPart left, right;

    public ShoulderModel() {
        super();

        ModelData modelData = new ModelData();

        modelData.getRoot()
                .addChild("left", ModelPartBuilder.create(), ModelTransform.NONE)
                .addChild("left",
                        ModelPartBuilder.create()
                                .uv(0, 0)
                                .cuboid(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f),
                        ModelTransform.of(5, -1, 0, 0, 0, (float)(-Math.PI * 0.125f)));

        modelData.getRoot()
                .addChild("right", ModelPartBuilder.create(), ModelTransform.NONE)
                .addChild("right",
                        ModelPartBuilder.create()
                                .uv(0, 0)
                                .cuboid(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f),
                        ModelTransform.of(-5, -1, 0, 0, (float)Math.PI, (float)(Math.PI * 0.125f)));


        ModelPart model = TexturedModelData.of(modelData, 16, 16).createModel();
        left = model.getChild("left");
        right = model.getChild("right");
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Arrays.asList(left, right);
    }

    @Override
    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        left.copyTransform(model.leftArm);
        right.copyTransform(model.rightArm);
        super.copyFromModel(model, slot);
    }
}
