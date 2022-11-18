package immersive_armors;

import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.mixin.MixinItemRenderer;
import immersive_armors.network.NetworkManagerImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class ClientMain {
    public static void postLoad() {
        //register colored items
        MixinItemRenderer itemRenderer = (MixinItemRenderer)MinecraftClient.getInstance().getItemRenderer();
        for (Supplier<Item> item : Items.coloredItems.values()) {
            itemRenderer.getColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableExtendedArmorItem)stack.getItem()).getColor(stack), item.get());
        }

        //initialize network manager
        Main.networkManager = new NetworkManagerImpl();

        //finish the items
        ItemsClient.setupPieces();
    }
}
