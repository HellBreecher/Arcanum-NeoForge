package com.hellbreecher.arcanum.common.lib;

import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;

public class ArcanumBlockTags {

    public static final TagKey<Block> NEEDS_BLOODDIAMOND_TOOL = blockTag("needs_bloodiamond_tool");
    public static final TagKey<Block> NEEDS_VOIDDIAMOND_TOOL = blockTag("needs_voiddiamond_tool");
    public static final TagKey<Block> NEEDS_INFERNALDIAMOND_TOOL = blockTag("needs_infernaldiamond_tool");
    public static final TagKey<Block> NEEDS_INFERNAL_TOOL = blockTag("needs_infernal_tool");

    public static final TagKey<Block> INCORRECT_FOR_BLOODDIAMOND_TOOL = blockTag("incorrect_for_bloodiamond_tool");
    public static final TagKey<Block> INCORRECT_FOR_VOIDDIAMOND_TOOL = blockTag("incorrect_for_voiddiamond_tool");
    public static final TagKey<Block> INCORRECT_FOR_INFERNALDIAMOND_TOOL = blockTag("incorrect_for_infernaldiamond_tool");
    public static final TagKey<Block> INCORRECT_FOR_INFERNAL_TOOL = blockTag("incorrect_for_infernal_tool");

    private ArcanumBlockTags() {}

    private static TagKey<Block> blockTag(String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }
}
