package immersive_armors.util;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

public class EnumCodec<E extends Enum<E>> implements PrimitiveCodec<E>{
    private final Class<E> enumClass;

    public EnumCodec(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public <T> DataResult<E> read(DynamicOps<T> ops, T input) {
        return ops.getNumberValue(input)
                .flatMap(n -> {
                    int index = n.intValue();
                    E[] values = enumClass.getEnumConstants();
                    return (index >= 0 && index < values.length)
                            ? DataResult.success(values[index])
                            : DataResult.error(() -> "Invalid enum ordinal: " + index);
                });
    }

    @Override
    public <T> T write(DynamicOps<T> ops, E value) {
        return ops.createInt(value.ordinal());
    }
}
