package immersive_armors.network.c2s;

import immersive_armors.armor_effects.ArmorEffect;
import immersive_armors.cobalt.network.Message;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorCommandMessage extends Message {
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorCommandMessage> STREAM_CODEC = StreamCodec.ofMember(ArmorCommandMessage::encode, ArmorCommandMessage::new);
    public static final CustomPacketPayload.Type<ArmorCommandMessage> TYPE = Message.createType("armor_command");

    private final EquipmentSlot slot;
    private final String command;

    public ArmorCommandMessage(int slot, String command) {
        this(EquipmentSlot.values()[slot], command);
    }

    public ArmorCommandMessage(EquipmentSlot slot, String command) {
        super();
        this.slot = slot;
        this.command = command;
    }

    public ArmorCommandMessage(RegistryFriendlyByteBuf b) {
        slot = b.readEnum(EquipmentSlot.class);
        command = b.readUtf();
    }

    @Override
    public void encode(RegistryFriendlyByteBuf b) {
        b.writeEnum(slot);
        b.writeUtf(command);
    }

    @Override
    public void receiveServer(ServerPlayer player) {
        ItemStack stack = player.getItemBySlot(slot);
        if (stack.getItem() instanceof ExtendedArmorItem item) {
            for (ArmorEffect e : item.getExtendedMaterial().getEffects()) {
                e.receiveCommand(stack, player.level(), player, slot.ordinal(), command);
            }
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
