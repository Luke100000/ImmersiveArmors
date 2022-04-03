package immersive_armors;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.mixin.MixinItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    public static final String MOD_ID = "immersive_armors";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void postLoad() {
        //register colored items
        MixinItemRenderer itemRenderer = (MixinItemRenderer)MinecraftClient.getInstance().getItemRenderer();
        for (Item[] items : Items.coloredItems) {
            itemRenderer.getColorMap().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((ExtendedArmorItem)stack.getItem()).getColor(stack), items);
        }
    }
}
