package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;

public abstract class DecoHeadModel extends DecoModel {
    public DecoHeadModel() {
        super();
    }

    abstract ModelPart getPart();

    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        getPart().copyTransform(model.head);
        super.copyFromModel(model, slot);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.singletonList(getPart());
    }
}
