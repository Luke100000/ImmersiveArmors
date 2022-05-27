package immersive_armors;

import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.mixin.MixinItemRenderer;
import immersive_armors.network.ClientNetworkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;

public class ClientMain {
    public static void postLoad() {
        //register colored items
        MixinItemRenderer itemRenderer = (MixinItemRenderer)MinecraftClient.getInstance().getItemRenderer();
        for (Item item : Items.coloredItems) {
            itemRenderer.getColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableExtendedArmorItem)stack.getItem()).getColor(stack), item);
        }

        //initialize network manager
        Main.networkManager = new ClientNetworkManager();

        //finish the items
        ItemsClient.setupPieces();
    }
}
