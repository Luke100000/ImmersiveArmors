package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;

public abstract class LayerPiece extends Piece {
    protected final SpriteAtlasTexture armorTrimsAtlas;

    protected abstract BipedEntityModel<LivingEntity> getModel();

    protected static BipedEntityModel<LivingEntity> buildDilatedModel(float dilation) {
        return buildDilatedModel(dilation, dilation);
    }

    protected static BipedEntityModel<LivingEntity> buildDilatedModel(float dilation, float headDilation) {
        return new BipedEntityModel<>(TexturedModelData.of(getHeadAdjustedModelData(new Dilation(dilation), new Dilation(headDilation), 0.0f), 64, 32).createModel());
    }

    public static ModelData getHeadAdjustedModelData(Dilation dilation, Dilation headDilation, float pivotOffsetY) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, headDilation.add(0.5F)), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(-5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(5.0F, 2.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(-1.9F, 12.0F + pivotOffsetY, 0.0F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(1.9F, 12.0F + pivotOffsetY, 0.0F));
        return modelData;
    }

    public LayerPiece() {
        armorTrimsAtlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    protected void renderTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, BipedEntityModel<LivingEntity> model, boolean leggings) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims()));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
        if (itemStack.getItem() instanceof ExtendedArmorItem armorItem) {
            //noinspection unchecked
            armorModel.copyBipedStateTo((BipedEntityModel<T>) getModel());
            setVisible(getModel(), armorSlot);

            if (isColored()) {
                int i = ((DyeableItem) armorItem).getColor(itemStack);
                float red = (float) (i >> 16 & 255) / 255.0F;
                float green = (float) (i >> 8 & 255) / 255.0F;
                float blue = (float) (i & 255) / 255.0F;
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), red, green, blue, false);
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 1.0F, 1.0F, 1.0F, true);
            } else {
                renderParts(matrices, vertexConsumers, light, itemStack, armorItem, getModel(), 1.0F, 1.0F, 1.0F, false);
            }

            ArmorTrim.getTrim(entity.getWorld().getRegistryManager(), itemStack).ifPresent(trim -> this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, trim, getModel(), armorSlot == EquipmentSlot.LEGS));
        }
    }
}
