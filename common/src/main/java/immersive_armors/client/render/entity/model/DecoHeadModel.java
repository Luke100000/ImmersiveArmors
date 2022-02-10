package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;

public abstract class DecoHeadModel extends AnimalModel {
    abstract ModelPart getPart();

    public void copyFromModel(BipedEntityModel model) {
        getPart().copyTransform(model.head);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.singletonList(getPart());
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.emptyList();
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
