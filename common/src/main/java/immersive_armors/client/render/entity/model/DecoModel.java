package immersive_armors.client.render.entity.model;

import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoModel implements ModelPartProvider {
    public DecoModel() {
    }

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of();
    }

    public static ModelPart getModelPart(HumanoidModel model, String name) {
        return switch (name) {
            case "head" -> model.head;
            case "leftArm" -> model.leftArm;
            case "rightArm" -> model.rightArm;
            case "leftLeg" -> model.leftLeg;
            case "rightLeg" -> model.rightLeg;
            default -> model.body;
        };
    }

    protected static void copyPart(ModelPart target, ModelPart source) {
        target.loadPose(source.storePose());
        target.visible = source.visible;
    }
}
