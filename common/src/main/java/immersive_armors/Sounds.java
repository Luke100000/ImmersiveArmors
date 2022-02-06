package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Sounds {
    SoundEvent EXAMPLE = register("example_sound");

    static void bootstrap() {
    }

    static SoundEvent register(String sound) {
        Identifier id = new Identifier(Main.MOD_ID, sound);
        return Registration.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
