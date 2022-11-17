package immersive_armors.fabric;

import immersive_armors.Config;
import immersive_armors.Items;
import immersive_armors.Messages;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.fabric.cobalt.network.NetworkHandlerImpl;
import immersive_armors.fabric.cobalt.registration.RegistrationImpl;
import immersive_armors.network.s2c.SettingsMessage;
import immersive_armors.server.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.BinomialLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;

import java.util.Map;
import java.util.function.Supplier;

public final class CommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new RegistrationImpl();
        new NetworkHandlerImpl();

        Items.bootstrap();
        Messages.bootstrap();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> Command.register(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                NetworkHandler.sendToPlayer(new SettingsMessage(), handler.player)
        );

        // Populate loot tables with armor loot
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (Items.lootLookup.containsKey(id.toString())) {
                for (Map.Entry<Supplier<Item>, Float> entry : Items.lootLookup.get(id.toString()).entrySet()) {
                    tableBuilder.pool(LootPool.builder()
                            .rolls(new BinomialLootTableRange(1, entry.getValue() * Config.getInstance().lootChance))
                            .with(ItemEntry.builder(entry.getKey().get()))
                    );
                }
            }
        });
    }
}

