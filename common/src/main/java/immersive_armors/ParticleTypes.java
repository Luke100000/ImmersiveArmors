package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface ParticleTypes {
    // DefaultParticleType EXAMPLE = register("example_particle", Registration.ObjectBuilders.Particles.simpleParticle());

    static void bootstrap() {
    }

    static <T extends ParticleType<?>> T register(String name, T type) {
        return Registration.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, name), type);
    }
}
