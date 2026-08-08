package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public final class ArcanumDataGen {
    private ArcanumDataGen() {}

    public static void gatherClient(GatherDataEvent.Client event) {
        event.addProvider(new ItemModelDataGen(event.getGenerator().getPackOutput()));
    }

    public static void gatherServer(GatherDataEvent.Server event) {
        event.addProvider(new RecipeDataGen.Runner(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider()
        ));
        event.addProvider(new ArcanumBlockTagsProvider(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider()
        ));
        event.addProvider(new LootTableProvider(
                event.getGenerator().getPackOutput(),
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(BlockLootDataGen::new, LootContextParamSets.BLOCK)),
                event.getLookupProvider()
        ));
        event.addProvider(new DatapackBuiltinEntriesProvider(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider(),
                WorldGenDataGen.createRegistryBuilder(),
                Set.of(Arcanum.MODID)
        ));
    }
}
