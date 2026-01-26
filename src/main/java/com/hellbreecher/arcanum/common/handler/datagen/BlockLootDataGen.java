package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public final class BlockLootDataGen extends BlockLootSubProvider {
    public BlockLootDataGen(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        dropOther(ArcanumBlocks.greensapphireore_block.get(), ArcanumItems.greensapphire.get());
        dropOther(ArcanumBlocks.blooddiamondore_block.get(), ArcanumItems.blooddiamond.get());
        dropOther(ArcanumBlocks.voiddiamondore_block.get(), ArcanumItems.voiddiamond.get());
        add(ArcanumBlocks.vanillarandomore_block.get(), createVanillaRandomOreDrops());
        add(ArcanumBlocks.modrandomore_block.get(), createModRandomOreDrops());
        dropOther(ArcanumBlocks.deepslategreensapphireore_block.get(), ArcanumItems.greensapphire.get());
        dropOther(ArcanumBlocks.deepslateblooddiamondore_block.get(), ArcanumItems.blooddiamond.get());
        dropOther(ArcanumBlocks.deepslatevoiddiamondore_block.get(), ArcanumItems.voiddiamond.get());
        add(ArcanumBlocks.deepslatevanillarandomore_block.get(), createVanillaRandomOreDrops());
        add(ArcanumBlocks.deepslatemodrandomore_block.get(), createModRandomOreDrops());

        dropSelf(ArcanumBlocks.greensapphire_block.get());
        dropSelf(ArcanumBlocks.blooddiamond_block.get());
        dropSelf(ArcanumBlocks.voiddiamond_block.get());

        add(ArcanumBlocks.boneore_block.get(), createSingleItemTable(Items.BONE, UniformGenerator.between(1.0F, 5.0F)));
        add(ArcanumBlocks.fleshore_block.get(), createSingleItemTable(Items.ROTTEN_FLESH, UniformGenerator.between(1.0F, 5.0F)));
        add(ArcanumBlocks.sulfurore_block.get(), createSingleItemTable(Items.GUNPOWDER, UniformGenerator.between(1.0F, 5.0F)));

        add(ArcanumBlocks.bone_block.get(), createSingleItemTable(Items.BONE, UniformGenerator.between(1.0F, 4.0F)));
        add(ArcanumBlocks.flesh_block.get(), createSingleItemTable(Items.ROTTEN_FLESH, UniformGenerator.between(1.0F, 4.0F)));
        add(ArcanumBlocks.sulfur_block.get(), createSingleItemTable(Items.GUNPOWDER, UniformGenerator.between(1.0F, 4.0F)));

        dropOther(ArcanumBlocks.greensapphiretorch_block.get(), ArcanumBlocks.greensapphiretorch_block_item.get());
        dropOther(ArcanumBlocks.wall_greensapphiretorch_block.get(), ArcanumBlocks.greensapphiretorch_block_item.get());
        dropSelf(ArcanumBlocks.greensapphirecoal_block.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ArcanumBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .collect(Collectors.toList());
    }

    private static LootTable.Builder createVanillaRandomOreDrops() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT))
                                .add(LootItem.lootTableItem(Items.COAL))
                                .add(LootItem.lootTableItem(Items.DIAMOND))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP))
                                .add(LootItem.lootTableItem(Items.LAPIS_LAZULI)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.REDSTONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                );
    }

    private static LootTable.Builder createModRandomOreDrops() {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ArcanumItems.greensapphire.get()))
                                .add(LootItem.lootTableItem(ArcanumItems.blooddiamond.get()))
                                .add(LootItem.lootTableItem(ArcanumItems.voiddiamond.get()))
                );
    }
}
