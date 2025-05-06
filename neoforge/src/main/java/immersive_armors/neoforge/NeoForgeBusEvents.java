package immersive_armors.neoforge;

import immersive_armors.ClientMain;
import immersive_armors.Main;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.s2c.SettingsMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Main.MOD_ID)
public class NeoForgeBusEvents {
    public static boolean firstLoad = true;

    @SubscribeEvent
    public static void onClientStart(ClientTickEvent.Pre event) {
        //forge decided to be funny and won't trigger the client load event.
        if (firstLoad) {
            ClientMain.postLoad();
            firstLoad = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity player) {
            NetworkHandler.sendToPlayer(new SettingsMessage(), player);
        }
    }
}
