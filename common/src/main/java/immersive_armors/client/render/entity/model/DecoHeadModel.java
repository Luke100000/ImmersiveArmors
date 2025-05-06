package immersive_armors.client.render.entity.model;

import java.util.Collections;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoHeadModel extends DecoModel {
    public DecoHeadModel() {
        super();
    }

    abstract ModelPart getPart();

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        getPart().copyFrom(model.head);
        super.copyFromModel(model, slot);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.singletonList(getPart());
    }
}
