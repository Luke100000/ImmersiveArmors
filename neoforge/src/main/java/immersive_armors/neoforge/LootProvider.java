package immersive_armors.neoforge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.config.Config;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS;

public class LootProvider {
    public static void initialize(IEventBus bus) {
        if (Config.getInstance().lootChance > 0) {
            GLM.register(bus);
        }
    }

    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(GLOBAL_LOOT_MODIFIER_SERIALIZERS, Main.MOD_ID);

    @SuppressWarnings("unused")
    private static final
    DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ImmersiveArmorsLootModifier>> ARMOR_MODIFIER_SERIALIZER = GLM.register("armor_modifier_serializer", ImmersiveArmorsLootModifier.CODEC);

    @EventBusSubscriber(modid = Main.MOD_ID)
    public static class EventHandlers {
        @SubscribeEvent
        public static void runData(GatherDataEvent.Server event) {
            if (Config.getInstance().lootChance > 0) {
                event.addProvider(new DataProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), Main.MOD_ID));
            }
        }
    }

    private static class DataProvider extends GlobalLootModifierProvider {
        public DataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String id) {
            super(output, registries, id);
        }

        @Override
        protected void start() {
            for (String s : Items.lootLookup.keySet()) {
                add("armor_modifier_serializer_" + s, new ImmersiveArmorsLootModifier
                        (new LootItemCondition[]{
                                LootTableIdCondition.builder(Identifier.parse(s)).build()
                        }));
            }
        }
    }


    private static class ImmersiveArmorsLootModifier extends LootModifier {
        public static final Supplier<MapCodec<ImmersiveArmorsLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, ImmersiveArmorsLootModifier::new)));

        public ImmersiveArmorsLootModifier(final LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            Identifier id = context.getQueriedLootTableId();
            if (Items.lootLookup.containsKey(id.toString())) {
                for (Map.Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(id.toString()).entrySet()) {
                    if (context.getLevel().getRandom().nextFloat() < entry.getValue() * Config.getInstance().lootChance) {
                        generatedLoot.add(new ItemStack(entry.getKey().get()));
                    }
                }
            }

            return generatedLoot;
        }

        @Override
        public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
            return CODEC.get();
        }
    }
}
