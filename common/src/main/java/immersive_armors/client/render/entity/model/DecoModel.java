package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoModel extends AgeableListModel {
    public DecoModel() {
        super(true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f);
    }

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        this.attackTime = model.attackTime;
        this.riding = model.riding;
        this.young = model.young;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return Collections.emptyList();
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

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
}
