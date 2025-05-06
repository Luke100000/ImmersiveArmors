package immersive_armors.client.render.entity.piece;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

public abstract class LayerPiece extends Piece {
    protected final TextureAtlas armorTrimsAtlas;

    protected abstract HumanoidModel<LivingEntity> getModel();

    protected static HumanoidModel<LivingEntity> buildDilatedModel(float dilation) {
        return buildDilatedModel(dilation, dilation);
    }

    protected static HumanoidModel<LivingEntity> buildDilatedModel(float dilation, float headDilation) {
        return new HumanoidModel<>(LayerDefinition.create(getHeadAdjustedModelData(new CubeDeformation(dilation), new CubeDeformation(headDilation), 0.0f), 64, 32).bakeRoot());
    }

    public static MeshDefinition getHeadAdjustedModelData(CubeDeformation dilation, CubeDeformation headDilation, float pivotOffsetY) {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation), PartPose.offset(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation.extend(0.5F)), PartPose.offset(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation), PartPose.offset(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(-5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(-1.9F, 12.0F + pivotOffsetY, 0.0F));
        modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(1.9F, 12.0F + pivotOffsetY, 0.0F));
        return modelData;
    }

    public LayerPiece() {
        armorTrimsAtlas = Minecraft.getInstance().getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    protected void renderTrim(Holder<ArmorMaterial> material, PoseStack matrices, MultiBufferSource vertexConsumers, int light, ArmorTrim trim, HumanoidModel<LivingEntity> model, boolean leggings) {
        TextureAtlasSprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.innerTexture(material) : trim.outerTexture(material));
        VertexConsumer vertexConsumer = sprite.wrap(vertexConsumers.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFF);
    }

    @Override
    public <T extends LivingEntity, A extends HumanoidModel<T>> void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armorItem) {
            //noinspection unchecked
            armorModel.copyPropertiesTo((HumanoidModel<T>) getModel());
            setVisible(getModel(), armorSlot);

            if (armorItem instanceof DyeableExtendedArmorItem dyeableArmorItem) {
                int c = dyeableArmorItem.getColor(itemStack);

                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), c + 0xFF000000, false);
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 0xFFFFFFFF, true);
            } else {
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 0xFFFFFFFF, false);
            }

            ArmorTrim trim = itemStack.get(DataComponents.TRIM);
            if (trim != null) {
                this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, trim, getModel(), armorSlot == EquipmentSlot.LEGS);
            }
        }
    }
}
