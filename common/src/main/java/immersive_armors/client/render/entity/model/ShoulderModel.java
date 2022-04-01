package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

import java.util.Arrays;
import java.util.Collections;

public class ShoulderModel extends DecoModel {
    private final ModelPart left;
    private final ModelPart right;

    public ShoulderModel() {
        super();

        ModelPart part;

        left = new ModelPart(16, 16, 0, 0);
        part = new ModelPart(16, 16, 0, 0);
        part.addCuboid(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f);
        part.roll = (float)(-Math.PI * 0.125f);
        part.setPivot(5, -1, 0);
        left.addChild(part);

        right = new ModelPart(16, 16, 0, 0);
        part = new ModelPart(16, 16, 0, 0);
        part.addCuboid(-0.5f, -4f, -3.5f, 1.0f, 8.0f, 7.0f);
        part.roll = (float)(Math.PI * 0.125f);
        part.yaw = (float)Math.PI;
        part.setPivot(-5, -1, 0);
        right.addChild(part);
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
    public void copyFromModel(BipedEntityModel model) {
        copyFromPart(left, model.leftArm);
        copyFromPart(right, model.rightArm);
        super.copyFromModel(model);
    }
}
