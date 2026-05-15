package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;

public interface ModelPartProvider {
    Iterable<ModelPart> parts();
}
