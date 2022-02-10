package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;

public class HorizontalHeadModel extends AnimalModel {
    private final ModelPart part;

    public HorizontalHeadModel() {
        part = new ModelPart(this, 0, 0);
        part.addCuboid(-10.0F, -22.0F, -2.0F, 20.0F, 12.0F, 0.0F, 0.0f);
        part.setPivot(0.0F, 0.0F, 0.0F);
    }

    public void copyFromModel(BipedEntityModel model) {
        part.copyTransform(model.head);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.singletonList(part);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Collections.emptyList();
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
