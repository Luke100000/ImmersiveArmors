package immersive_armors.client.render.entity.piece;

import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.client.render.entity.piece.Piece;
import immersive_armors.item.ArmorPiece;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class CapePiece<M extends CapeModel<LivingEntity>> extends Piece {
    private final M model;

    public CapePiece(M model) {
        this.model = model;
    }

    private Identifier getCapeTexture(ExtendedArmorItem item, boolean overlay) {
        return new Identifier("immersive_armors", "textures/models/armor/" + item.getMaterial().getName() + "/cape" + (overlay ? "_overlay" : "") + ".png");
    }

    private Vec3d predictPosition(Entity entity, float tickDelta) {
        return new Vec3d(
                MathHelper.lerp(tickDelta, entity.prevX, entity.getX()),
                MathHelper.lerp(tickDelta, entity.prevY, entity.getY()),
                MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ())
        );
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, ItemStack itemStack, float tickDelta, ArmorPiece piece, BipedEntityModel<LivingEntity> bipedEntityModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem armor = (ExtendedArmorItem)itemStack.getItem();

            //update cape motion
            CapeAngles angles = new CapeAngles(itemStack);
            angles.updateCapeAngles(entity, tickDelta);
            angles.store(itemStack);

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

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float)(6.0F + r / 2.0F + q)));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)(s / 2.0F)));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)(180.0F - s / 2.0F)));

            model.setAngles(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            VertexConsumer vertexConsumer;
            if (piece.isColored()) {
                int i = ((DyeableItem)armor).getColor(itemStack);
                float red = (float)(i >> 16 & 255) / 255.0F;
                float green = (float)(i >> 8 & 255) / 255.0F;
                float blue = (float)(i & 255) / 255.0F;

                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, false)));
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);

                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, true)));
            } else {
                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(getCapeTexture(armor, false)));
            }
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pop();
        }
    }

    private class CapeAngles {
        private double capeX;
        private double capeY;
        private double capeZ;
        private double deltaX;
        private double deltaY;
        private double deltaZ;
        private float lastTickDelta;

        private void updateCapeAngles(Entity entity, float tickDelta) {
            Vec3d pos = predictPosition(entity, tickDelta);

            double deltaX = pos.getX() - capeX;
            double deltaY = pos.getY() - capeY;
            double deltaZ = pos.getZ() - capeZ;

            if (deltaX > 10.0D || deltaX < -10D) {
                this.capeX = pos.getX();
                deltaX = 0;
            }

            if (deltaY > 10.0D || deltaY < -10D) {
                this.capeY = pos.getY();
                deltaY = 0;
            }

            if (deltaZ > 10.0D || deltaZ < -10D) {
                this.capeZ = pos.getZ();
                deltaZ = 0;
            }

            float delta = tickDelta - lastTickDelta;
            if (delta < 0.0) {
                delta = 1.0f + delta;
            }
            delta *= 0.25f;
            lastTickDelta = tickDelta;

            this.capeX += deltaX * delta;
            this.capeZ += deltaZ * delta;
            this.capeY += deltaY * delta;

            this.deltaX = capeX - pos.getX();
            this.deltaY = capeY - pos.getY();
            this.deltaZ = capeZ - pos.getZ();
        }

        public CapeAngles(ItemStack cape) {
            NbtCompound tag = cape.getOrCreateTag();
            if (tag.contains("capeAngles")) {
                NbtCompound angles = tag.getCompound("capeAngles");
                capeX = angles.getDouble("capeX");
                capeY = angles.getDouble("capeY");
                capeZ = angles.getDouble("capeZ");
                lastTickDelta = angles.getFloat("lastTickDelta");
            }
        }

        public void store(ItemStack cape) {
            NbtCompound tag = cape.getOrCreateTag();
            NbtCompound angles = new NbtCompound();
            angles.putDouble("capeX", capeX);
            angles.putDouble("capeY", capeY);
            angles.putDouble("capeZ", capeZ);
            angles.putFloat("lastTickDelta", lastTickDelta);
            tag.put("capeAngles", angles);
        }
    }
}
