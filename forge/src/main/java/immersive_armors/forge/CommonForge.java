package immersive_armors.forge;

import immersive_armors.ItemGroups;
import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.Messages;
import immersive_armors.forge.cobalt.network.NetworkHandlerImpl;
import immersive_armors.forge.cobalt.registration.RegistrationImpl;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.registry.RegistryKeys.ITEM_GROUP;


@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public final class CommonForge {
    static {
        Main.FORGE = true;
    }

    public CommonForge() {
        RegistrationImpl.bootstrap();
        new NetworkHandlerImpl();

        LootProvider.initialize();

        DEF_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onRegistryEvent(RegisterEvent event) {
        Items.bootstrap();
        Messages.bootstrap();
    }

    public static final DeferredRegister<ItemGroup> DEF_REG = DeferredRegister.create(ITEM_GROUP, Main.MOD_ID);

    public static final RegistryObject<ItemGroup> TAB = DEF_REG.register(Main.MOD_ID, () -> ItemGroup.builder()
            .displayName(ItemGroups.getDisplayName())
            .icon(ItemGroups::getIcon)
            .entries((featureFlags, output) -> output.addAll(Items.getSortedItems()))
            .build()
    );
}
