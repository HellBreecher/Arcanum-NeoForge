package com.hellbreecher.arcanum.common.items.tools;

import com.hellbreecher.arcanum.core.Config;

import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InfernalHoeItem extends HoeItem {

    public InfernalHoeItem(Identifier id) {
        super(ArcanumToolMaterials.InfernalTool, -2.0F, -3.0F, new Item.Properties()
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (!Config.INFERNAL_TOOLS_IGNORE_HARVEST_LEVEL.get()) {
            return super.isCorrectToolForDrops(stack, state);
        }
        return isCorrectForTool(state, BlockTags.MINEABLE_WITH_HOE);
    }

    private static boolean isCorrectForTool(BlockState state, TagKey<Block> toolTag) {
        boolean inAnyToolTag = state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_HOE)
                || state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
        return state.is(toolTag) || !inAnyToolTag;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Player player) {
        Level level = player.level();
        if (!Config.ENABLE_CRAFTED_ENCHANTMENTS.get()) {
            return;
        }
        if (level.isClientSide() || stack.isEnchanted()) {
            return;
        }

        enchant(stack, level, Enchantments.FORTUNE, 5);
        enchant(stack, level, Enchantments.UNBREAKING, 10);
        enchant(stack, level, Enchantments.MENDING, 1);
        enchant(stack, level, Enchantments.FIRE_ASPECT, 5);
    }

    private static void enchant(ItemStack stack, Level level, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }
}



