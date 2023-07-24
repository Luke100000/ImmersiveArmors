package immersive_armors.network.c2s;

import immersive_armors.armor_effects.ArmorEffect;
import immersive_armors.cobalt.network.Message;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class ArmorCommandMessage extends Message {
    private final int slot;
    private final String command;

    public ArmorCommandMessage(int slot, String command) {
        super();
        this.slot = slot;
        this.command = command;
    }

    public ArmorCommandMessage(PacketByteBuf b) {
        slot = b.readInt();
        command = b.readString();
    }

    @Override
    public void encode(PacketByteBuf b) {
        b.writeInt(slot);
        b.writeString(command);
    }

    @Override
    public void receive(PlayerEntity player) {
        ItemStack stack = player.getInventory().getStack(slot);
        if (stack.getItem() instanceof ExtendedArmorItem item) {
            for (ArmorEffect e : item.getMaterial().getEffects()) {
                e.receiveCommand(stack, player.getWorld(), player, slot, command);
            }
        }
    }
}
