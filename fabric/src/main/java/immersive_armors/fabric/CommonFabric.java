package immersive_armors.fabric;

import immersive_armors.*;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.config.Config;
import immersive_armors.fabric.cobalt.network.NetworkHandlerImpl;
import immersive_armors.fabric.cobalt.registration.RegistrationImpl;
import immersive_armors.network.s2c.SettingsMessage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import java.util.Map;
import java.util.function.Supplier;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Items.registerCauldronInteractions();
        Messages.bootstrap();
        CustomDataComponentTypes.bootstrap();

        CreativeModeTab group = FabricItemGroup.builder()
                .title(ItemGroups.getDisplayName())
                .icon(ItemGroups::getIcon)
                .displayItems((enabledFeatures, entries) -> entries.acceptAll(Items.getSortedItems()))
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Main.locate("group"), group);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                NetworkHandler.sendToPlayer(new SettingsMessage(), handler.player)
        );

        // Populate loot tables with armor loot
        // TODO: Test
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (Items.lootLookup.containsKey(key.toString())) {
                for (Map.Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(key.toString()).entrySet()) {
                    tableBuilder.withPool(LootPool.lootPool()
                            .setRolls(BinomialDistributionGenerator.binomial(1, entry.getValue() * Config.getInstance().lootChance))
                            .add(LootItem.lootTableItem(entry.getKey().get()))
                    );
                }
            }
        });
    }
}
