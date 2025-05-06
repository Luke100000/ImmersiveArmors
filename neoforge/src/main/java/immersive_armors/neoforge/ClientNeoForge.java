package immersive_armors.neoforge;

import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.item.ExtendedArmorItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod(value = Main.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ClientNeoForge {
    @SubscribeEvent
    public static void initItemColors(RegisterColorHandlersEvent.Item event) {
        Items.coloredItems.forEach((id, item) -> {
            if (item.get() instanceof ExtendedArmorItem armor) {
                event.register(Items.getDyeColor(armor.getExtendedMaterial().getColor()), armor);
            }
        });
    }
}
