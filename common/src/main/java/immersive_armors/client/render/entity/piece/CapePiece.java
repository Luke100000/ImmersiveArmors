package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import immersive_armors.Main;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CapePiece<M extends CapeModel<LivingEntity>> extends Piece {
    private static final Map<UUID, CapeAngles> capeAngles = new HashMap<>();

    private final M model;

    public CapePiece(M model) {
        this.model = model;
    }

    private ResourceLocation getCapeTexture(ExtendedArmorItem item, boolean overlay) {
        return Main.locate("textures/models/armor/" + item.getExtendedMaterial().getName() + "/cape" + (overlay ? "_overlay" : "") + ".png");
    }

    public <T extends LivingEntity, A extends HumanoidModel<T>> void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armor) {
            //update cape motion
            CapeAngles angles = capeAngles.computeIfAbsent(entity.getUUID(), k -> new CapeAngles());
            angles.updateCapeAngles(entity, tickDelta);

            matrices.pushPose();
            matrices.translate(0.0D, 0.0D, 0.125D);

            float n = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO);
            double o = Mth.sin(n * 0.017453292F);
            double p = -Mth.cos(n * 0.017453292F);
            double q = angles.deltaY * 40.0F;
            q = Mth.clamp(q, -6.0F, 32.0F);
            double r = (angles.deltaX * o + angles.deltaZ * p) * 100.0F;
            r = Mth.clamp(r, 0.0F, 150.0F);
            double s = (angles.deltaX * p - angles.deltaZ * o) * 100.0F;
            s = Mth.clamp(s, -20.0F, 20.0F);
            if (r < 0.0F) {
                r = 0.0F;
            }

            if (entity.isCrouching()) {
                q += 22.5F;
                matrices.translate(0.0, 0.25, 0.0);
            }

            matrices.mulPose(Axis.XP.rotationDegrees((float) (6.0F + r / 2.0F + q)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float) (s / 2.0F)));
            matrices.mulPose(Axis.YP.rotationDegrees((float) (180.0F - s / 2.0F)));

            model.setupAnim(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            VertexConsumer vertexConsumer;
            if (armor instanceof DyeableExtendedArmorItem dyeableArmorItem) {
                int c = dyeableArmorItem.getColor(itemStack);

                vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(getCapeTexture(armor, false)));
                model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, c);

                vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(getCapeTexture(armor, true)));
            } else {
                vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(getCapeTexture(armor, false)));
            }
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            matrices.popPose();
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

        private Vec3 predictPosition(Entity entity, float tickDelta) {
            return new Vec3(
                    Mth.lerp(tickDelta, entity.xo, entity.getX()),
                    Mth.lerp(tickDelta, entity.yo, entity.getY()),
                    Mth.lerp(tickDelta, entity.zo, entity.getZ())
            );
        }

        private void updateCapeAngles(Entity entity, float tickDelta) {
            Vec3 pos = predictPosition(entity, tickDelta);

            double dx = pos.x() - capeX;
            double dy = pos.y() - capeY;
            double dz = pos.z() - capeZ;

            if (dx > 10.0D || dx < -10D) {
                this.capeX = pos.x();
                dx = 0;
            }

            if (dy > 10.0D || dy < -10D) {
                this.capeY = pos.y();
                dy = 0;
            }

            if (dz > 10.0D || dz < -10D) {
                this.capeZ = pos.z();
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

            this.deltaX = capeX - pos.x();
            this.deltaY = capeY - pos.y();
            this.deltaZ = capeZ - pos.z();
        }
    }
}
