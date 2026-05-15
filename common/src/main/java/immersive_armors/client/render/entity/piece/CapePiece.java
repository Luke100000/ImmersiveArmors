package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import immersive_armors.Main;
import immersive_armors.client.render.entity.ImmersiveArmorRenderState;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CapePiece<M extends CapeModel<?>> extends Piece {
    private static final Map<UUID, CapeAngles> capeAngles = new HashMap<>();

    private final M model;

    public CapePiece(M model) {
        this.model = model;
    }

    private Identifier getCapeTexture(ExtendedArmorItem item, boolean overlay) {
        return Main.locate("textures/models/armor/" + item.getExtendedMaterial().getName() + "/cape" + (overlay ? "_overlay" : "") + ".png");
    }

    @Override
    public int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armor) {
            LivingEntity entity = ((ImmersiveArmorRenderState) renderState).immersiveArmors$getEntity();
            matrices.pushPose();
            matrices.translate(0.0D, 0.0D, 0.125D);

            double q = 0.0F;
            double r = 0.0F;
            double s = 0.0F;
            if (entity != null) {
                CapeAngles angles = capeAngles.computeIfAbsent(entity.getUUID(), k -> new CapeAngles());
                angles.updateCapeAngles(entity, tickDelta);

                float n = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO);
                double o = Mth.sin(n * 0.017453292F);
                double p = -Mth.cos(n * 0.017453292F);
                q = angles.deltaY * 40.0F;
                q = Mth.clamp(q, -6.0F, 32.0F);
                r = (angles.deltaX * o + angles.deltaZ * p) * 100.0F;
                r = Mth.clamp(r, 0.0F, 150.0F);
                s = (angles.deltaX * p - angles.deltaZ * o) * 100.0F;
                s = Mth.clamp(s, -20.0F, 20.0F);
            }

            if (renderState.isCrouching) {
                q += 22.5F;
                matrices.translate(0.0, 0.25, 0.0);
            }

            matrices.mulPose(Axis.XP.rotationDegrees((float)(6.0F + r / 2.0F + q)));
            matrices.mulPose(Axis.ZP.rotationDegrees((float)(s / 2.0F)));
            matrices.mulPose(Axis.YP.rotationDegrees((float)(180.0F - s / 2.0F)));

            if (armor instanceof DyeableExtendedArmorItem dyeableArmorItem) {
                int c = dyeableArmorItem.getColor(itemStack);
                order = renderCape(matrices, submitNodeCollector, light, getCapeTexture(armor, false), c, order);
                order = renderCape(matrices, submitNodeCollector, light, getCapeTexture(armor, true), 0xFFFFFFFF, order);
            } else {
                order = renderCape(matrices, submitNodeCollector, light, getCapeTexture(armor, false), 0xFFFFFFFF, order);
            }
            matrices.popPose();
        }
        return order;
    }

    private int renderCape(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, Identifier texture, int color, int order) {
        return renderGeometry(matrices, submitNodeCollector, RenderTypes.armorCutoutNoCull(texture), light, OverlayTexture.NO_OVERLAY, color, model.parts(), order);
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
