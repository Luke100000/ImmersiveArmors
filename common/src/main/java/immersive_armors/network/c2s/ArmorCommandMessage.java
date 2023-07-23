package immersive_armors.network.c2s;

import immersive_armors.armorEffects.ArmorEffect;
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
        ItemStack stack = player.inventory.getStack(slot);
        if (stack.getItem() instanceof ExtendedArmorItem) {
            ExtendedArmorItem item = (ExtendedArmorItem)stack.getItem();
            for (ArmorEffect e : item.getMaterial().getEffects()) {
                e.receiveCommand(stack, player.world, player, slot, command);
            }
        }
    }
}
