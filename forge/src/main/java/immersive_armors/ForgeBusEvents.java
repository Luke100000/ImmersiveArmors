package immersive_armors;

import immersive_armors.server.Command;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ForgeBusEvents {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onClientStart(FMLClientSetupEvent event) {
        ClientMain.postLoad();
    }
}
