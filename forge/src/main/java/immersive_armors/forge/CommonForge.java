package immersive_armors.forge;

import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.Messages;
import immersive_armors.forge.cobalt.network.NetworkHandlerImpl;
import immersive_armors.forge.cobalt.registration.RegistrationImpl;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public final class CommonForge {
    static {
        Main.FORGE = true;
    }

    public CommonForge() {
        RegistrationImpl.bootstrap();
        new NetworkHandlerImpl();

        Messages.bootstrap();

        Items.bootstrap();
    }
}
