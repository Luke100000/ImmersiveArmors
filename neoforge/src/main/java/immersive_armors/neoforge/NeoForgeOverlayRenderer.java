package immersive_armors.neoforge;

import immersive_armors.Main;
import immersive_armors.client.OverlayRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class NeoForgeOverlayRenderer {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Main.locate("ia_overlay"),
                (graphics, delta) -> OverlayRenderer.renderOverlay(graphics));
    }
}
