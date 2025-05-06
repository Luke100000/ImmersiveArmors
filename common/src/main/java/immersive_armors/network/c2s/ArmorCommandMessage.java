package immersive_armors.network.c2s;

import immersive_armors.armor_effects.ArmorEffect;
import immersive_armors.cobalt.network.Message;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class ArmorCommandMessage extends Message {
    public static final PacketCodec<RegistryByteBuf, ArmorCommandMessage> STREAM_CODEC = PacketCodec.of(ArmorCommandMessage::encode, ArmorCommandMessage::new);
    public static final CustomPayload.Id<ArmorCommandMessage> TYPE = Message.createType("armor_command");

    private final int slot;
    private final String command;

    public ArmorCommandMessage(int slot, String command) {
        super();
        this.slot = slot;
        this.command = command;
    }

    public ArmorCommandMessage(RegistryByteBuf b) {
        slot = b.readInt();
        command = b.readString();
    }

    @Override
    public void encode(RegistryByteBuf b) {
        b.writeInt(slot);
        b.writeString(command);
    }

    @Override
    public void receiveServer(ServerPlayerEntity player) {
        ItemStack stack = player.getInventory().getStack(slot);
        if (stack.getItem() instanceof ExtendedArmorItem item) {
            for (ArmorEffect e : item.getExtendedMaterial().getEffects()) {
                e.receiveCommand(stack, player.getWorld(), player, slot, command);
            }
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return TYPE;
    }
}
