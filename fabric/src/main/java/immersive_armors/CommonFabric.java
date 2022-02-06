package immersive_armors;

import immersive_armors.cobalt.network.NetworkHandlerImpl;
import immersive_armors.cobalt.registration.RegistrationImpl;
import immersive_armors.server.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Sounds.bootstrap();
        ParticleTypes.bootstrap();

        ServerTickEvents.END_WORLD_TICK.register(w -> {

        });
        ServerTickEvents.END_SERVER_TICK.register(s -> {

        });

        ServerPlayerEvents.AFTER_RESPAWN.register((old, neu, alive) -> {

        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

        });

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            Command.register(dispatcher);
        });
    }
}

