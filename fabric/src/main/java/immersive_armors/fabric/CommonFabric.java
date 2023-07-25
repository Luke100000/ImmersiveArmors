package immersive_armors.fabric;

import immersive_armors.ItemGroups;
import immersive_armors.Items;
import immersive_armors.Main;
import immersive_armors.Messages;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.config.Config;
import immersive_armors.fabric.cobalt.network.NetworkHandlerImpl;
import immersive_armors.fabric.cobalt.registration.RegistrationImpl;
import immersive_armors.network.s2c.SettingsMessage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Map;
import java.util.function.Supplier;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Messages.bootstrap();

        ItemGroup group = FabricItemGroup.builder()
                .displayName(ItemGroups.getDisplayName())
                .icon(ItemGroups::getIcon)
                .entries((enabledFeatures, entries) -> entries.addAll(Items.getSortedItems()))
                .build();

        Registry.register(Registries.ITEM_GROUP, Main.locate("group"), group);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                NetworkHandler.sendToPlayer(new SettingsMessage(), handler.player)
        );

        // Populate loot tables with armor loot
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (Items.lootLookup.containsKey(id.toString())) {
                for (Map.Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(id.toString()).entrySet()) {
                    tableBuilder.pool(LootPool.builder()
                            .rolls(BinomialLootNumberProvider.create(1, entry.getValue() * Config.getInstance().lootChance))
                            .with(ItemEntry.builder(entry.getKey().get()))
                    );
                }
            }
        });
    }
}

