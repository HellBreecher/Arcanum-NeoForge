package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.common.lib.ArcanumBlockTags;
import com.hellbreecher.arcanum.core.Arcanum;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public final class ArcanumBlockTagsProvider extends BlockTagsProvider {
    public ArcanumBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Arcanum.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ArcanumBlocks.greensapphireore_block.get(),
                ArcanumBlocks.blooddiamondore_block.get(),
                ArcanumBlocks.voiddiamondore_block.get(),
                ArcanumBlocks.vanillarandomore_block.get(),
                ArcanumBlocks.modrandomore_block.get(),
                ArcanumBlocks.deepslategreensapphireore_block.get(),
                ArcanumBlocks.deepslateblooddiamondore_block.get(),
                ArcanumBlocks.deepslatevoiddiamondore_block.get(),
                ArcanumBlocks.deepslatevanillarandomore_block.get(),
                ArcanumBlocks.deepslatemodrandomore_block.get(),
                ArcanumBlocks.greensapphire_block.get(),
                ArcanumBlocks.blooddiamond_block.get(),
                ArcanumBlocks.voiddiamond_block.get(),
                ArcanumBlocks.infernalcrystal_block.get(),
                ArcanumBlocks.boneore_block.get(),
                ArcanumBlocks.fleshore_block.get(),
                ArcanumBlocks.sulfurore_block.get(),
                ArcanumBlocks.bone_block.get(),
                ArcanumBlocks.flesh_block.get(),
                ArcanumBlocks.sulfur_block.get(),
                ArcanumBlocks.greensapphirecoal_block.get(),
                ArcanumBlocks.voiddiamondfurnace_block.get(),
                ArcanumBlocks.sapphirefurnace_block.get(),
                ArcanumBlocks.blooddiamondfurnace_block.get(),
                ArcanumBlocks.infernalfurnace_block.get()
        );
        tag(BlockTags.NEEDS_IRON_TOOL).add(
                ArcanumBlocks.greensapphireore_block.get(),
                ArcanumBlocks.deepslategreensapphireore_block.get()
        );
        tag(BlockTags.NEEDS_STONE_TOOL).add(
                ArcanumBlocks.infernalcrystal_block.get()
        );
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                ArcanumBlocks.blooddiamondore_block.get(),
                ArcanumBlocks.deepslateblooddiamondore_block.get()
        );

        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).add(
                ArcanumBlocks.voiddiamondore_block.get(),
                ArcanumBlocks.deepslatevoiddiamondore_block.get()
        );

        tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).add(
                ArcanumBlocks.voiddiamondore_block.get(),
                ArcanumBlocks.deepslatevoiddiamondore_block.get()
        );

        tag(ArcanumBlockTags.NEEDS_BLOODDIAMOND_TOOL).add(
                ArcanumBlocks.voiddiamondore_block.get(),
                ArcanumBlocks.deepslatevoiddiamondore_block.get()
        );

        tag(ArcanumBlockTags.NEEDS_VOIDDIAMOND_TOOL);
        tag(ArcanumBlockTags.NEEDS_INFERNALDIAMOND_TOOL);
        tag(ArcanumBlockTags.NEEDS_INFERNAL_TOOL);

        tag(ArcanumBlockTags.INCORRECT_FOR_BLOODDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_VOIDDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_INFERNALDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_INFERNAL_TOOL);
    }
}
