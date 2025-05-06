package immersive_armors.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class EnumPacketCodec<E extends Enum<E>> implements StreamCodec<ByteBuf, E> {
    private final Class<E> enumClass;

    public EnumPacketCodec(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public E decode(ByteBuf buf) {
        return enumClass.getEnumConstants()[buf.readInt()];
    }

    @Override
    public void encode(ByteBuf buf, E value) {
        buf.writeInt(value.ordinal());
    }
}
