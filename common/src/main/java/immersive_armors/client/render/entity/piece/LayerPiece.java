package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.AtlasIds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.ArmorTrim;

import java.util.List;

public abstract class LayerPiece extends Piece {
    protected abstract HumanoidModel getModel();

    protected static HumanoidModel buildDilatedModel(float dilation) {
        return buildDilatedModel(dilation, dilation);
    }

    protected static HumanoidModel buildDilatedModel(float dilation, float headDilation) {
        return new HumanoidModel<>(LayerDefinition.create(getHeadAdjustedModelData(new CubeDeformation(dilation), new CubeDeformation(headDilation), 0.0f), 64, 32).bakeRoot());
    }

    public static MeshDefinition getHeadAdjustedModelData(CubeDeformation dilation, CubeDeformation headDilation, float pivotOffsetY) {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation), PartPose.offset(0.0F, 0.0F + pivotOffsetY, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation.extend(0.5F)), PartPose.ZERO);
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation), PartPose.offset(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(-5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(-1.9F, 12.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(1.9F, 12.0F + pivotOffsetY, 0.0F));
        return modelData;
    }

    @Override
    public int render(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidRenderState renderState, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, HumanoidModel<HumanoidRenderState> armorModel, int order) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armorItem) {
            HumanoidModel<HumanoidRenderState> model = getModel();
            copyHumanoid(armorModel, model);
            setVisible(model, armorSlot);
            Iterable<ModelPart> parts = List.of(model.root());

            if (armorItem instanceof DyeableExtendedArmorItem dyeableArmorItem) {
                int c = dyeableArmorItem.getColor(itemStack);

                order = renderParts(matrices, submitNodeCollector, light, renderState, itemStack, armorItem, parts, c, false, order);
                order = renderParts(matrices, submitNodeCollector, light, renderState, itemStack, armorItem, parts, 0xFFFFFFFF, true, order);
            } else {
                order = renderParts(matrices, submitNodeCollector, light, renderState, itemStack, armorItem, parts, 0xFFFFFFFF, false, order);
            }

            ArmorTrim trim = itemStack.get(DataComponents.TRIM);
            if (trim != null) {
                order = renderTrim(matrices, submitNodeCollector, light, trim, parts, armorItem, armorSlot, order);
            }
        }
        return order;
    }

    protected int renderTrim(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, ArmorTrim trim, Iterable<ModelPart> parts, ExtendedArmorItem armorItem, EquipmentSlot armorSlot, int order) {
        EquipmentClientInfo.LayerType layerType = armorSlot == EquipmentSlot.LEGS ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID;
        TextureAtlas armorTrimsAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.ARMOR_TRIMS);
        TextureAtlasSprite sprite = armorTrimsAtlas.getSprite(trim.layerAssetId(layerType.trimAssetPrefix(), armorItem.getExtendedMaterial().getMaterial().assetId()));
        RenderType renderType = Sheets.armorTrimsSheet(trim.pattern().value().decal());
        return renderGeometry(matrices, submitNodeCollector, renderType, light, OverlayTexture.NO_OVERLAY, -1, parts, order, sprite);
    }

    private static void copyHumanoid(HumanoidModel source, HumanoidModel target) {
        target.head.loadPose(source.head.storePose());
        target.hat.loadPose(source.hat.storePose());
        target.body.loadPose(source.body.storePose());
        target.rightArm.loadPose(source.rightArm.storePose());
        target.leftArm.loadPose(source.leftArm.storePose());
        target.rightLeg.loadPose(source.rightLeg.storePose());
        target.leftLeg.loadPose(source.leftLeg.storePose());
    }
}
