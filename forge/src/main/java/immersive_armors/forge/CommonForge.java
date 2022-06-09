package immersive_armors.forge;

import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.Messages;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.forge.cobalt.network.NetworkHandlerImpl;
import immersive_armors.forge.cobalt.registration.RegistrationImpl;
import immersive_armors.network.s2c.SettingsMessage;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    }

    @SubscribeEvent
    public static void onRegistryEvent(RegistryEvent<Item> event) {
        Items.bootstrap();
    }

    @SubscribeEvent
    public static void onCreateEntityAttributes(EntityAttributeCreationEvent event) {
        RegistrationImpl.ENTITY_ATTRIBUTES.forEach((type, attributes) -> event.put(type, attributes.get().build()));
    }
}
