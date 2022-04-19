package immersive_armors;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.mixin.MixinItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;

public class ClientMain {
    public static void postLoad() {
        //register colored items
        MixinItemRenderer itemRenderer = (MixinItemRenderer)MinecraftClient.getInstance().getItemRenderer();
        for (Item item : Items.coloredItems) {
            itemRenderer.getColorMap().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((ExtendedArmorItem)stack.getItem()).getColor(stack), item);
        }

        //finish the items
        ItemsClient.setupPieces();
    }
}
