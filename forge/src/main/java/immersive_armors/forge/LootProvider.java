package immersive_armors.forge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import immersive_armors.Config;
import immersive_armors.Items;
import immersive_armors.Main;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class LootProvider {
    public static void initialize() {
        if (Config.getInstance().lootChance > 0) {
            GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Main.MOD_ID);

    @SuppressWarnings("unused")
    private static final RegistryObject<Codec<ImmersiveArmorsLootModifier>> ARMOR_MODIFIER_SERIALIZER = GLM.register("armor_modifier_serializer", ImmersiveArmorsLootModifier.CODEC);

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandlers {
        @SubscribeEvent
        public static void runData(GatherDataEvent event) {
            if (Config.getInstance().lootChance > 0) {
                event.getGenerator().addProvider(event.includeServer(), new DataProvider(event.getGenerator().getPackOutput(), Main.MOD_ID));
            }
        }
    }

    private static class DataProvider extends GlobalLootModifierProvider {
        public DataProvider(DataOutput output, String id) {
            super(output, id);
        }

        @Override
        protected void start() {
            for (String s : Items.lootLookup.keySet()) {
                add("armor_modifier_serializer_" + s, new ImmersiveArmorsLootModifier
                        (new LootCondition[] {
                                LootTableIdCondition.builder(new Identifier(s)).build()
                        }));
            }
        }
    }


    private static class ImmersiveArmorsLootModifier extends LootModifier {
        public static final Supplier<Codec<ImmersiveArmorsLootModifier>> CODEC = Suppliers
                .memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
                        .apply(inst, ImmersiveArmorsLootModifier::new)
                ));

        public ImmersiveArmorsLootModifier(final LootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
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

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC.get();
        }
    }
}