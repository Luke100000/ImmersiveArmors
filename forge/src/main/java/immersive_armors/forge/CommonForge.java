package immersive_armors.forge;

import immersive_armors.ItemGroups;
import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.Messages;
import immersive_armors.forge.cobalt.network.NetworkHandlerImpl;
import immersive_armors.forge.cobalt.registration.RegistrationImpl;
import net.minecraftforge.event.CreativeModeTabEvent;
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

        Items.bootstrap();

        LootProvider.initialize();
    }

    @SubscribeEvent
    public static void register(CreativeModeTabEvent.Register event) {
        ItemGroups.ARMOR = event.registerCreativeModeTab(ItemGroups.getIdentifier(), builder -> builder
                .displayName(ItemGroups.getDisplayName())
                .icon(ItemGroups::getIcon)
                .entries((featureFlags, output) -> output.addAll(Items.getSortedItems()))
        );
    }
}
