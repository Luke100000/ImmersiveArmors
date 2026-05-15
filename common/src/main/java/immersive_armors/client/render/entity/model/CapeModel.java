package immersive_armors.client.render.entity.model;

import java.util.List;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;

public class CapeModel<T extends LivingEntity> implements ModelPartProvider {
    private final ModelPart cape;

    public CapeModel() {
        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot().addOrReplaceChild("cape",
                CubeListBuilder.create()
                        .addBox(-5.0F, 0.0F, -2.0F, 10.0F, 16.0F, 1.0F),
                PartPose.ZERO);


        ModelPart model = LayerDefinition.create(modelData, 32, 32).bakeRoot();
        cape = model.getChild("cape");
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of(cape);
    }
}
