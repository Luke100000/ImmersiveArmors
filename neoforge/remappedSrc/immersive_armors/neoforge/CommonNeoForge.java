package immersive_armors.neoforge;

import immersive_armors.*;
import immersive_armors.neoforge.cobalt.network.NetworkHandlerImpl;
import immersive_armors.neoforge.cobalt.registration.RegistrationImpl;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import static net.minecraft.core.registries.BuiltInRegistries.CREATIVE_MODE_TAB;

@Mod(Main.MOD_ID)
@EventBusSubscriber(modid = Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CommonNeoForge {
    static {
        Main.FORGE = true;
    }

    static final NetworkHandlerImpl NETWORK_HANDLER = new NetworkHandlerImpl();

    public CommonNeoForge(IEventBus bus) {
        new RegistrationImpl(bus);

        LootProvider.initialize(bus);

        DEF_REG.register(bus);
    }

    static boolean onlyOnce = true;

    @SubscribeEvent
    public static void onRegistryEvent(RegisterEvent event) {
        if (onlyOnce) {
            onlyOnce = false;
            Items.bootstrap();
            Messages.bootstrap();
            CustomDataComponentTypes.bootstrap();
        }
    }

    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(CREATIVE_MODE_TAB, Main.MOD_ID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = DEF_REG.register(Main.MOD_ID, () -> CreativeModeTab.builder()
            .title(ItemGroups.getDisplayName())
            .icon(ItemGroups::getIcon)
            .displayItems((featureFlags, output) -> output.acceptAll(Items.getSortedItems()))
            .build()
    );

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        CommonNeoForge.NETWORK_HANDLER.register(event);
    }
}
