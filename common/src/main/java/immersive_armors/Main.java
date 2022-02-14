package immersive_armors;

import immersive_armors.client.render.entity.feature.ExtendedArmorFeatureRenderer;
import immersive_armors.client.render.entity.feature.ExtendedCapeFeatureRenderer;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.mixin.MixinArmorFeatureRenderer;
import immersive_armors.mixin.MixinLivingEntityRenderer;
import immersive_armors.mixin.MixingEntityRenderDispatcher;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void postLoad() {
        MixingEntityRenderDispatcher renderManager = (MixingEntityRenderDispatcher)MinecraftClient.getInstance().getEntityRenderDispatcher();
        renderManager.getModelRenderers().forEach((unused, playerEntityRenderer) -> process(playerEntityRenderer));
        renderManager.getRenderers().forEach((unused, entityRenderer) -> {
            if (entityRenderer instanceof FeatureRendererContext) {
                process((FeatureRendererContext<?, ?>)entityRenderer);
            }
        });
    }

    private static void process(FeatureRendererContext<?, ?> renderer) {
        if (renderer instanceof LivingEntityRenderer) {
            MixinLivingEntityRenderer livingRenderer = (MixinLivingEntityRenderer)renderer;
            List<FeatureRenderer<?, ?>> features = livingRenderer.getFeatures();
            List<FeatureRenderer<?, ?>> toRemove = new LinkedList<>();
            List<FeatureRenderer<?, ?>> toAdd = new LinkedList<>();
            for (FeatureRenderer<?, ?> feature : features) {
                if (feature.getClass() == ArmorFeatureRenderer.class) {
                    MixinArmorFeatureRenderer<?, ?, ?> armorFeature = (MixinArmorFeatureRenderer<?, ?, ?>)feature;
                    toRemove.add(feature);
                    //noinspection unchecked
                    toAdd.add(new ExtendedArmorFeatureRenderer(
                            renderer,
                            armorFeature.getLeggingsModel(),
                            armorFeature.getBodyModel()
                    ));
                    //noinspection unchecked
                    toAdd.add(new ExtendedCapeFeatureRenderer(renderer, new CapeModel()));
                    LOGGER.info("Replace Armor Renderer of " + renderer);
                } else if (feature.getClass() == CapeFeatureRenderer.class) {
                    LOGGER.info("Replace Cape Renderer of " + renderer);
                    toRemove.add(feature);
                }
            }
            while (!toRemove.isEmpty()) {
                features.remove(toRemove.remove(0));
            }
            while (!toAdd.isEmpty()) {
                features.add(toAdd.remove(0));
            }
        }
    }
}
