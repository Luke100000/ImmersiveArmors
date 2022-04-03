package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

import java.util.Collections;

public class RightVerticalShoulderModel extends DecoModel {
    private final ModelPart model;

    public RightVerticalShoulderModel() {
        super();

        model = new ModelPart(32, 16, 0, 0);

        model.addCuboid(-5.0f, -4f, -4f, 1.0f, 8.0f, 8.0f);
        model.addCuboid(-2.5f, -4f, -4f, 1.0f, 8.0f, 8.0f);
        model.addCuboid(0.0f, -4f, -4f, 1.0f, 8.0f, 8.0f);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.singletonList(model);
    }

    @Override
    public void copyFromModel(BipedEntityModel model) {
        copyFromPart(this.model, model.rightArm);
        super.copyFromModel(model);
    }
}
