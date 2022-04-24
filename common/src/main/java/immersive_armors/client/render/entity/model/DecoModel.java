package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;

public abstract class DecoModel extends AnimalModel {
    public DecoModel() {
        super(true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f);
    }

    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        this.handSwingProgress = model.handSwingProgress;
        this.riding = model.riding;
        this.child = model.child;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.emptyList();
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    public static ModelPart getModelPart(BipedEntityModel model, String name) {
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
