package immersive_armors;

import immersive_armors.client.particle.ExampleParticle;
import immersive_armors.cobalt.registration.RegistrationImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public final class ClientForge {
    @SubscribeEvent
    public static void data(FMLConstructModEvent event) {

    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        RegistrationImpl.bootstrap();
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        RegistrationImpl.bootstrap();
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.particleManager.registerFactory(ParticleTypes.EXAMPLE, ExampleParticle.Factory::new);
    }
}
