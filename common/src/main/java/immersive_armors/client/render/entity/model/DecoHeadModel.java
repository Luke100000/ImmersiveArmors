package immersive_armors.client.render.entity.model;

import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class DecoHeadModel extends DecoModel {
    public DecoHeadModel() {
        super();
    }

    abstract ModelPart getPart();

    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        copyPart(getPart(), model.head);
        super.copyFromModel(model, slot);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of(getPart());
    }
}
