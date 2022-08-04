package immersive_armors.network.c2s;

import immersive_armors.armorEffects.ArmorEffect;
import immersive_armors.cobalt.network.Message;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ArmorCommandMessage implements Message {
    private final int slot;
    private final String command;

    public ArmorCommandMessage(int slot, String command) {
        this.slot = slot;
        this.command = command;
    }

    @Override
    public void receive(PlayerEntity player) {
        ItemStack stack = player.getInventory().getStack(slot);
        if (stack.getItem() instanceof ExtendedArmorItem item) {
            for (ArmorEffect e : item.getMaterial().getEffects()) {
                e.receiveCommand(stack, player.world, player, slot, command);
            }
        }
    }
}
