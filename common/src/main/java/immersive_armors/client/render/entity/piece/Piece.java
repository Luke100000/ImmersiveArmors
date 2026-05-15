package immersive_armors.client.render.entity.piece;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import immersive_armors.config.Config;
import immersive_armors.item.ExtendedArmorItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public abstract class Piece {
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

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

    private Identifier getTexture(ExtendedArmorItem item, boolean overlay) {
        String string = "immersive_armors:textures/models/armor/" + item.getExtendedMaterial().getName() + "/" + getTexture() + (overlay ? "_overlay" : "") + ".png";
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::parse);
    }

    protected int renderParts(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, ExtendedArmorItem item, Iterable<ModelPart> parts, int color, boolean overlay, int order) {
        RenderType renderLayer;
        if (isTranslucent()) {
            renderLayer = RenderTypes.entityTranslucent(getTexture(item, overlay));
        } else if (isGlowing()) {
            renderLayer = RenderTypes.beaconBeam(getTexture(item, overlay), false);
        } else {
            renderLayer = RenderTypes.armorCutoutNoCull(getTexture(item, overlay));
        }

        order = renderGeometry(matrices, submitNodeCollector, renderLayer, light, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color, parts, order);
        if (hasGlint() || itemStack.hasFoil() && Config.getInstance().enableEnchantmentGlint) {
            order = renderGeometry(matrices, submitNodeCollector, RenderTypes.armorEntityGlint(), light, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color, parts, order);
        }
        return order;
    }

    protected int renderGeometry(PoseStack matrices, SubmitNodeCollector submitNodeCollector, RenderType renderType, int light, int overlay, int color, Iterable<ModelPart> parts, int order) {
        return renderGeometry(matrices, submitNodeCollector, renderType, light, overlay, color, parts, order, null);
    }

    protected int renderGeometry(PoseStack matrices, SubmitNodeCollector submitNodeCollector, RenderType renderType, int light, int overlay, int color, Iterable<ModelPart> parts, int order, TextureAtlasSprite sprite) {
        List<CubeRender> cubes = collectCubes(matrices, parts);
        if (!cubes.isEmpty()) {
            submitNodeCollector.order(order++).submitCustomGeometry(new PoseStack(), renderType, (pose, vertexConsumer) -> {
                VertexConsumer buffer = sprite == null ? vertexConsumer : sprite.wrap(vertexConsumer);
                cubes.forEach(cube -> cube.cube().compile(cube.pose(), buffer, light, overlay, color));
            });
        }
        return order;
    }

    private List<CubeRender> collectCubes(PoseStack matrices, Iterable<ModelPart> parts) {
        List<CubeRender> cubes = new ArrayList<>();
        parts.forEach(part -> collectCubes(matrices, part, cubes));
        return cubes;
    }

    private void collectCubes(PoseStack matrices, ModelPart part, List<CubeRender> cubes) {
        part.visit(matrices, (pose, path, index, cube) -> cubes.add(new CubeRender(pose.copy(), cube)));
    }

    public abstract int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order);

    private record CubeRender(PoseStack.Pose pose, ModelPart.Cube cube) {
    }

    private boolean translucent;
    private boolean glint;
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

    public boolean isGlowing() {
        return glowing;
    }

    public String getTexture() {
        return texture;
    }
}
