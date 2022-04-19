package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;

public class RightVerticalShoulderModel extends DecoModel {
    private final ModelPart part;

    public RightVerticalShoulderModel() {
        super();

        part = new ModelPart(32, 16, 0, 0);

        part.addCuboid(-5.0f, -4f, -4f, 1.0f, 8.0f, 8.0f);
        part.addCuboid(-2.5f, -4f, -4f, 1.0f, 8.0f, 8.0f);
        part.addCuboid(0.0f, -4f, -4f, 1.0f, 8.0f, 8.0f);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.singletonList(part);
    }

    @Override
    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        part.copyTransform(model.rightArm);
        super.copyFromModel(model, slot);
    }
}
