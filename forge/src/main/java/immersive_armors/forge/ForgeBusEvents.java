package immersive_armors.forge;

import immersive_armors.ClientMain;
import immersive_armors.Main;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.s2c.SettingsMessage;
import immersive_armors.server.Command;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ForgeBusEvents {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }

    public static boolean firstLoad = true;

    @SubscribeEvent
    public static void onClientStart(TickEvent.ClientTickEvent event) {
        //forge decided to be funny and won't trigger the client load event
        if (firstLoad) {
            ClientMain.postLoad();
            firstLoad = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
            NetworkHandler.sendToPlayer(new SettingsMessage(), player);
        }
    }
}
