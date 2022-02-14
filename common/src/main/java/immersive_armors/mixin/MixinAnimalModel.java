package immersive_armors.mixin;

import net.minecraft.client.render.entity.model.AnimalModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnimalModel.class)
public interface MixinAnimalModel {
    @Accessor
    boolean getHeadScaled();

    @Accessor
    float getChildHeadYOffset();

    @Accessor
    float getChildHeadZOffset();

    @Accessor
    float getInvertedChildHeadScale();

    @Accessor
    float getInvertedChildBodyScale();

    @Accessor
    float getChildBodyYOffset();
}
