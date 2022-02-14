package immersive_armors.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class CapeModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart cloak;

    public CapeModel() {
        this.cloak = new ModelPart(this, 0, 0);
        this.cloak.setTextureSize(32, 32);
        this.cloak.addCuboid(-5.0F, 0.0F, -2.0F, 10.0F, 16.0F, 1.0F, 0.0f);
    }

    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.cloak.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
