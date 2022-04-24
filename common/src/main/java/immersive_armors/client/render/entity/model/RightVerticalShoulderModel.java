package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;

public class RightVerticalShoulderModel extends DecoModel {
    private final ModelPart part;

    public RightVerticalShoulderModel() {
        super();

        ModelData modelData = new ModelData();

        modelData.getRoot().addChild("part",
                ModelPartBuilder.create()
                        .cuboid(-5.0f, -4f, -4f, 1.0f, 8.0f, 8.0f)
                        .cuboid(-2.5f, -4f, -4f, 1.0f, 8.0f, 8.0f)
                        .cuboid(0.0f, -4f, -4f, 1.0f, 8.0f, 8.0f),
                ModelTransform.NONE);


        ModelPart model = TexturedModelData.of(modelData, 32, 16).createModel();
        part = model.getChild("part");
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
