package immersive_armors.fabric;

import immersive_armors.ClientMain;
import immersive_armors.Items;
import immersive_armors.item.ExtendedArmorItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public final class ClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(event -> ClientMain.postLoad());

        // Register item colors
        Items.coloredItems.forEach((id, item) -> {
            if (item.get() instanceof ExtendedArmorItem armor) {
                ColorProviderRegistry.ITEM.register(Items.getDyeColor(armor.getExtendedMaterial().getColor()), armor);
            }
        });

        // Register armor renderers
        Items.items.values().forEach(item -> {
            if (item.get() instanceof ExtendedArmorItem) {
                ArmorRenderer.register(new ImmersiveArmorRenderer(), item.get());
            }
        });
    }
}
