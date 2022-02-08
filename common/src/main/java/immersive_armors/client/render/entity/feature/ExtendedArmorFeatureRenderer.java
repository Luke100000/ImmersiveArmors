package immersive_armors.client.render.entity.feature;

import com.google.common.collect.Maps;
import immersive_armors.item.ArmorLayer;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.mixin.MixinArmorFeatureRenderer;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ExtendedArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends ArmorFeatureRenderer<T, M, A> {
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    private final A leggingsModelLower;
    private final A bodyModelLower;

    private final A leggingsModelUpper;
    private final A bodyModelUpper;

    @SuppressWarnings("unchecked")
    public ExtendedArmorFeatureRenderer(FeatureRendererContext<T, M> context, A leggingsModel, A bodyModel) {
        super(context, leggingsModel, bodyModel);

        leggingsModelLower = (A)new BipedEntityModel<T>(0.1f);
        bodyModelLower = (A)new BipedEntityModel<T>(0.6f);

        leggingsModelUpper = (A)new BipedEntityModel<T>(1.0f);
        bodyModelUpper = (A)new BipedEntityModel<T>(1.5f);
    }

    private boolean usesSecondLayer(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    @SuppressWarnings("unchecked")
    private A getArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? ((MixinArmorFeatureRenderer<T, M, A>)this).getLeggingsModel() : ((MixinArmorFeatureRenderer<T, M, A>)this).getBodyModel();
    }

    private A getLowerArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? leggingsModelLower : bodyModelLower;
    }

    private A getUpperArmor(EquipmentSlot slot) {
        return this.usesSecondLayer(slot) ? leggingsModelUpper : bodyModelUpper;
    }

    private Identifier getArmorTexture(ArmorItem item, boolean legs, @Nullable String overlay, ArmorLayer armorLayer) {
        String string;
        if (item instanceof ExtendedArmorItem) {
            string = "immersive_armors:textures/models/armor/" + item.getMaterial().getName() + "/layer_" + (legs ? 2 : 1) + "_" + armorLayer.name().toLowerCase(Locale.ENGLISH) + (overlay == null ? "" : "_" + overlay) + ".png";
        } else {
            string = "textures/models/armor/" + item.getMaterial().getName() + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
        }
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, A model, boolean legs, float red, float green, float blue, @Nullable String overlay, ArmorLayer armorLayer) {
        RenderLayer renderLayer;
        if (item instanceof ExtendedArmorItem && ((ExtendedArmorItem)item).getMaterial().isTranslucent(armorLayer)) {
            renderLayer = RenderLayer.getEntityTranslucent(getArmorTexture(item, legs, overlay, armorLayer));
        } else {
            renderLayer = RenderLayer.getArmorCutoutNoCull(getArmorTexture(item, legs, overlay, armorLayer));
        }
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, renderLayer, false, glint);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, ArmorLayer armorLayer) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)itemStack.getItem();
            if (armorItem.getSlotType() == armorSlot && hasLayer(armorItem, armorLayer)) {
                this.getContextModel().setAttributes(model);
                this.setVisible(model, armorSlot);
                boolean secondLayer = this.usesSecondLayer(armorSlot);
                boolean hasGlint = itemStack.hasGlint();
                if (isColored(armorItem, armorLayer)) {
                    int i = ((DyeableItem)armorItem).getColor(itemStack);
                    float f = (float)(i >> 16 & 255) / 255.0F;
                    float g = (float)(i >> 8 & 255) / 255.0F;
                    float h = (float)(i & 255) / 255.0F;
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, f, g, h, null, armorLayer);
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, 1.0F, 1.0F, 1.0F, "overlay", armorLayer);
                } else {
                    renderArmorParts(matrices, vertexConsumers, light, armorItem, hasGlint, model, secondLayer, 1.0F, 1.0F, 1.0F, null, armorLayer);
                }

            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        // middle layer
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, getArmor(EquipmentSlot.CHEST), ArmorLayer.MIDDLE);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, getArmor(EquipmentSlot.LEGS), ArmorLayer.MIDDLE);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, getArmor(EquipmentSlot.FEET), ArmorLayer.MIDDLE);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, getArmor(EquipmentSlot.HEAD), ArmorLayer.MIDDLE);

        // lower layer
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, getLowerArmor(EquipmentSlot.CHEST), ArmorLayer.LOWER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, getLowerArmor(EquipmentSlot.LEGS), ArmorLayer.LOWER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, getLowerArmor(EquipmentSlot.FEET), ArmorLayer.LOWER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, getLowerArmor(EquipmentSlot.HEAD), ArmorLayer.LOWER);

        // upper layer
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, getUpperArmor(EquipmentSlot.CHEST), ArmorLayer.UPPER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, getUpperArmor(EquipmentSlot.LEGS), ArmorLayer.UPPER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, getUpperArmor(EquipmentSlot.FEET), ArmorLayer.UPPER);
        renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, getUpperArmor(EquipmentSlot.HEAD), ArmorLayer.UPPER);
    }

    private boolean hasLayer(ArmorItem item, ArmorLayer layer) {
        if (item instanceof ExtendedArmorItem) {
            return ((ExtendedArmorItem)item).getMaterial().hasLayer(layer);
        } else {
            return layer == ArmorLayer.MIDDLE;
        }
    }

    private boolean isColored(ArmorItem item, ArmorLayer layer) {
        if (item instanceof ExtendedArmorItem) {
            return ((ExtendedArmorItem)item).getMaterial().isColored(layer);
        } else {
            return layer == ArmorLayer.MIDDLE && item instanceof DyeableArmorItem;
        }
    }
}
