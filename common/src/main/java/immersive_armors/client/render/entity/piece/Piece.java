package immersive_armors.client.render.entity.piece;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class Piece {
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    public Piece() {

    }

    protected void setVisible(HumanoidModel bipedModel, EquipmentSlot slot) {
        bipedModel.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
            }
            case CHEST -> {
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
            }
            case LEGS -> {
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
            case FEET -> {
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
        }
    }

    private ResourceLocation getTexture(ExtendedArmorItem item, boolean overlay) {
        String string = "immersive_armors:textures/models/armor/" + item.getExtendedMaterial().getName() + "/" + getTexture() + (overlay ? "_overlay" : "") + ".png";
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::parse);
    }

    protected void renderParts(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack itemStack, ExtendedArmorItem item, EntityModel model, int color, boolean overlay) {
        RenderType renderLayer;
        if (isTranslucent()) {
            renderLayer = RenderType.entityTranslucent(getTexture(item, overlay));
        } else if (isGlowing()) {
            renderLayer = RenderType.beaconBeam(getTexture(item, overlay), false);
        } else {
            renderLayer = RenderType.armorCutoutNoCull(getTexture(item, overlay));
        }
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, renderLayer, hasGlint() | itemStack.hasFoil() & Config.getInstance().enableEnchantmentGlint);
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    public abstract <T extends LivingEntity, A extends HumanoidModel<T>> void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel);

    private boolean translucent;
    private boolean glint;
    private boolean colored;
    private boolean glowing;

    private String texture;

    public Piece translucent() {
        this.translucent = true;
        return this;
    }

    public Piece glint() {
        this.glint = true;
        return this;
    }

    public Piece colored() {
        this.colored = true;
        return this;
    }

    public Piece glowing() {
        this.glowing = true;
        return this;
    }

    public Piece texture(String texture) {
        this.texture = texture;
        return this;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public boolean hasGlint() {
        return glint;
    }

    public boolean isColored() {
        return colored;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public String getTexture() {
        return texture;
    }
}
