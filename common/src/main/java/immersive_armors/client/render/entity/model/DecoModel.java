package immersive_armors.client.render.entity.model;

import immersive_armors.mixin.MixinAnimalModel;
import java.util.Collections;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;

public abstract class DecoModel extends AnimalModel {
    public DecoModel(MixinAnimalModel model) {
        super(
                model.getHeadScaled(),
                model.getChildHeadYOffset(),
                model.getChildHeadZOffset(),
                model.getInvertedChildHeadScale(),
                model.getInvertedChildBodyScale(),
                model.getChildBodyYOffset()
        );
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
