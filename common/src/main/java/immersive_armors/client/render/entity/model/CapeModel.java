package immersive_armors.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class CapeModel<T extends LivingEntity> extends EntityModel<T> {
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
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.cape.render(matrices, vertices, light, overlay, color);
    }
}
