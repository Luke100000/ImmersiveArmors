package immersive_armors;

import immersive_armors.cobalt.network.NetworkHandlerImpl;
import immersive_armors.cobalt.registration.RegistrationImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public final class CommonForge {
    public CommonForge() {
        RegistrationImpl.bootstrap();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Sounds.bootstrap();
        ParticleTypes.bootstrap();
    }

    @SubscribeEvent
    public static void onCreateEntityAttributes(EntityAttributeCreationEvent event) {
        RegistrationImpl.ENTITY_ATTRIBUTES.forEach((type, attributes) -> {
            event.put(type, attributes.get().build());
        });
    }
}
