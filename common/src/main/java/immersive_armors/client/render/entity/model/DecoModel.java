package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;

import java.util.Collections;

public abstract class DecoModel extends AnimalModel {
    public DecoModel() {
        super(true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f);
    }

    public void copyFromModel(BipedEntityModel model) {
        this.handSwingProgress = model.handSwingProgress;
        this.riding = model.riding;
        this.child = model.child;
    }

    protected void copyFromPart(ModelPart to, ModelPart from) {
        to.copyTransform(from);
        to.visible = from.visible;
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
}
