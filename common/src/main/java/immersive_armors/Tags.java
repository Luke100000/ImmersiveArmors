package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public interface Tags {
    interface Blocks {
        Tag<Block> EXAMPLE = register("example_blocks");

        static void bootstrap() {
        }

        static Tag<Block> register(String path) {
            return Registration.ObjectBuilders.Tags.block(new Identifier(Main.MOD_ID, path));
        }
    }

    interface Items {
        Tag<Item> EXAMPLE = register("example_items");

        static void bootstrap() {
        }

        static Tag<Item> register(String path) {
            return Registration.ObjectBuilders.Tags.item(new Identifier(Main.MOD_ID, path));
        }
    }
}
