package immersive_armors.fabric;

import immersive_armors.Items;
import immersive_armors.fabric.cobalt.network.NetworkHandlerImpl;
import immersive_armors.fabric.cobalt.registration.RegistrationImpl;
import immersive_armors.server.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            Command.register(dispatcher);
        });
    }
}

