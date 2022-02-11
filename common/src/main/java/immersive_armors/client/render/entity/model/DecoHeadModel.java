package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public abstract class DecoHeadModel extends DecoModel {
    abstract ModelPart getPart();

    public void copyFromModel(BipedEntityModel model) {
        copyFromPart(getPart(), model.head);
        super.copyFromModel(model);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.singletonList(getPart());
    }
}
