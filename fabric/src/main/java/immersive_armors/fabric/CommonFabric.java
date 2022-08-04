package immersive_armors.fabric;

import immersive_armors.Items;
import immersive_armors.Messages;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.fabric.cobalt.network.NetworkHandlerImpl;
import immersive_armors.fabric.cobalt.registration.RegistrationImpl;
import immersive_armors.network.s2c.SettingsMessage;
import immersive_armors.server.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Messages.bootstrap();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> Command.register(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                NetworkHandler.sendToPlayer(new SettingsMessage(), handler.player)
        );
    }
}

