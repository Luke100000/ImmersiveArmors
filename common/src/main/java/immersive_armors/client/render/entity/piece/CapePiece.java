package immersive_armors.client.render.entity.piece;

import immersive_armors.Main;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CapePiece<M extends CapeModel<LivingEntity>> extends Piece {
    private static final Map<UUID, CapeAngles> capeAngles = new HashMap<>();

    private final M model;

    public CapePiece(M model) {
        this.model = model;
    }

    private Identifier getCapeTexture(ExtendedArmorItem item, boolean overlay) {
        return Main.locate("textures/models/armor/" + item.getExtendedMaterial().getName() + "/cape" + (overlay ? "_overlay" : "") + ".png");
    }

    public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armor) {
            //update cape motion
            CapeAngles angles = capeAngles.computeIfAbsent(entity.getUuid(), k -> new CapeAngles());
            angles.updateCapeAngles(entity, tickDelta);

            matrices.push();
            matrices.translate(0.0D, 0.0D, 0.125D);

            float n = entity.prevBodyYaw + (entity.bodyYaw - entity.prevBodyYaw);
            double o = MathHelper.sin(n * 0.017453292F);
            double p = -MathHelper.cos(n * 0.017453292F);
            double q = angles.deltaY * 40.0F;
            q = MathHelper.clamp(q, -6.0F, 32.0F);
            double r = (angles.deltaX * o + angles.deltaZ * p) * 100.0F;
            r = MathHelper.clamp(r, 0.0F, 150.0F);
            double s = (angles.deltaX * p - angles.deltaZ * o) * 100.0F;
            s = MathHelper.clamp(s, -20.0F, 20.0F);
            if (r < 0.0F) {
                r = 0.0F;
            }

            if (entity.isInSneakingPose()) {
                q += 22.5F;
                matrices.translate(0.0, 0.25, 0.0);
            }

            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (6.0F + r / 2.0F + q)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) (s / 2.0F)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (180.0F - s / 2.0F)));

            model.setAngles(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            VertexConsumer vertexConsumer;
            if (armor instanceof DyeableExtendedArmorItem dyeableArmorItem) {
                int c = dyeableArmorItem.getColor(itemStack);

                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, false)));
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, c);

                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, true)));
            } else {
                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, false)));
            }
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
            matrices.pop();
        }
    }

    private static class CapeAngles {
        private double capeX;
        private double capeY;
        private double capeZ;
        private double deltaX;
        private double deltaY;
        private double deltaZ;
        private float lastTickDelta;

        private Vec3d predictPosition(Entity entity, float tickDelta) {
            return new Vec3d(
                    MathHelper.lerp(tickDelta, entity.prevX, entity.getX()),
                    MathHelper.lerp(tickDelta, entity.prevY, entity.getY()),
                    MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ())
            );
        }

        private void updateCapeAngles(Entity entity, float tickDelta) {
            Vec3d pos = predictPosition(entity, tickDelta);

            double dx = pos.getX() - capeX;
            double dy = pos.getY() - capeY;
            double dz = pos.getZ() - capeZ;

            if (dx > 10.0D || dx < -10D) {
                this.capeX = pos.getX();
                dx = 0;
            }

            if (dy > 10.0D || dy < -10D) {
                this.capeY = pos.getY();
                dy = 0;
            }

            if (dz > 10.0D || dz < -10D) {
                this.capeZ = pos.getZ();
                dz = 0;
            }

            float delta = tickDelta - lastTickDelta;
            if (delta < 0.0) {
                delta = 1.0f + delta;
            }
            delta *= 0.25f;
            lastTickDelta = tickDelta;

            this.capeX += dx * delta;
            this.capeZ += dz * delta;
            this.capeY += dy * delta;

            this.deltaX = capeX - pos.getX();
            this.deltaY = capeY - pos.getY();
            this.deltaZ = capeZ - pos.getZ();
        }
    }
}
