package immersive_armors.forge;

import com.google.gson.JsonObject;
import immersive_armors.config.Config;
import immersive_armors.Items;
import immersive_armors.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class LootProvider {
    public static void initialize() {
        if (Config.getInstance().lootChance > 0) {
            GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Main.MOD_ID);
    private static final RegistryObject<ImmersiveArmorsLootModifier.Serializer> ARMOR_MODIFIER_SERIALIZER = GLM.register("armor_modifier_serializer", ImmersiveArmorsLootModifier.Serializer::new);

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandlers {
        @SubscribeEvent
        public static void runData(GatherDataEvent event) {
            if (Config.getInstance().lootChance > 0) {
                event.getGenerator().addProvider(new DataProvider(event.getGenerator(), Main.MOD_ID));
            }
        }
    }

    private static class DataProvider extends GlobalLootModifierProvider {
        public DataProvider(DataGenerator gen, String modId) {
            super(gen, modId);
        }

        @Override
        protected void start() {
            for (String s : Items.lootLookup.keySet()) {
                add("armor_modifier_serializer_" + s, ARMOR_MODIFIER_SERIALIZER.get(), new ImmersiveArmorsLootModifier(new LootCondition[] {
                        LootTableIdCondition.builder(new Identifier(s)).build()
                }));
            }
        }
    }

    public static class ImmersiveArmorsLootModifier extends LootModifier {
        public ImmersiveArmorsLootModifier(final LootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        protected @NotNull List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            Identifier id = context.getQueriedLootTableId();
            if (Items.lootLookup.containsKey(id.toString())) {
                for (Map.Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(id.toString()).entrySet()) {
                    if (context.getWorld().getRandom().nextFloat() < entry.getValue() * Config.getInstance().lootChance) {
                        generatedLoot.add(new ItemStack(entry.getKey().get()));
                    }
                }
            }

            return generatedLoot;
        }

        private static class Serializer extends GlobalLootModifierSerializer<ImmersiveArmorsLootModifier> {
            @Override
            public ImmersiveArmorsLootModifier read(Identifier location, JsonObject object, LootCondition[] conditions) {
                return new ImmersiveArmorsLootModifier(conditions);
            }

            @Override
            public JsonObject write(ImmersiveArmorsLootModifier instance) {
                return this.makeConditions(instance.conditions);
            }
        }
    }
}