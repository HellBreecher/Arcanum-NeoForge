package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.common.lib.ArcanumBlockTags;
import com.hellbreecher.arcanum.core.Arcanum;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public final class ArcanumBlockTagsProvider extends BlockTagsProvider {
    public ArcanumBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Arcanum.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                key(ArcanumBlocks.greensapphireore_block.get()),
                key(ArcanumBlocks.blooddiamondore_block.get()),
                key(ArcanumBlocks.voiddiamondore_block.get()),
                key(ArcanumBlocks.vanillarandomore_block.get()),
                key(ArcanumBlocks.modrandomore_block.get()),
                key(ArcanumBlocks.deepslategreensapphireore_block.get()),
                key(ArcanumBlocks.deepslateblooddiamondore_block.get()),
                key(ArcanumBlocks.deepslatevoiddiamondore_block.get()),
                key(ArcanumBlocks.deepslatevanillarandomore_block.get()),
                key(ArcanumBlocks.deepslatemodrandomore_block.get()),
                key(ArcanumBlocks.greensapphire_block.get()),
                key(ArcanumBlocks.blooddiamond_block.get()),
                key(ArcanumBlocks.voiddiamond_block.get()),
                key(ArcanumBlocks.infernalcrystal_block.get()),
                key(ArcanumBlocks.boneore_block.get()),
                key(ArcanumBlocks.fleshore_block.get()),
                key(ArcanumBlocks.sulfurore_block.get()),
                key(ArcanumBlocks.bone_block.get()),
                key(ArcanumBlocks.flesh_block.get()),
                key(ArcanumBlocks.sulfur_block.get()),
                key(ArcanumBlocks.greensapphirecoal_block.get()),
                key(ArcanumBlocks.voiddiamondfurnace_block.get()),
                key(ArcanumBlocks.sapphirefurnace_block.get()),
                key(ArcanumBlocks.blooddiamondfurnace_block.get()),
                key(ArcanumBlocks.infernalfurnace_block.get())
        );
        tag(BlockTags.NEEDS_IRON_TOOL).add(
                key(ArcanumBlocks.greensapphireore_block.get()),
                key(ArcanumBlocks.deepslategreensapphireore_block.get())
        );
        tag(BlockTags.NEEDS_STONE_TOOL).add(
                key(ArcanumBlocks.infernalcrystal_block.get())
        );
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                key(ArcanumBlocks.blooddiamondore_block.get()),
                key(ArcanumBlocks.deepslateblooddiamondore_block.get())
        );

        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).add(
                key(ArcanumBlocks.voiddiamondore_block.get()),
                key(ArcanumBlocks.deepslatevoiddiamondore_block.get())
        );

        tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).add(
                key(ArcanumBlocks.voiddiamondore_block.get()),
                key(ArcanumBlocks.deepslatevoiddiamondore_block.get())
        );

        tag(ArcanumBlockTags.NEEDS_BLOODDIAMOND_TOOL).add(
                key(ArcanumBlocks.voiddiamondore_block.get()),
                key(ArcanumBlocks.deepslatevoiddiamondore_block.get())
        );

        tag(ArcanumBlockTags.NEEDS_VOIDDIAMOND_TOOL);
        tag(ArcanumBlockTags.NEEDS_INFERNALDIAMOND_TOOL);
        tag(ArcanumBlockTags.NEEDS_INFERNAL_TOOL);

        tag(ArcanumBlockTags.INCORRECT_FOR_BLOODDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_VOIDDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_INFERNALDIAMOND_TOOL);
        tag(ArcanumBlockTags.INCORRECT_FOR_INFERNAL_TOOL);
    }

    private static ResourceKey<Block> key(Block block) {
        return block.builtInRegistryHolder().key();
    }
}
