package com.hellbreecher.arcanum.common.items.tools;

import com.hellbreecher.arcanum.core.Config;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InfernalShearsItem extends ShearsItem {

    public InfernalShearsItem(Identifier id) {
        super(new Item.Properties()
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (!Config.INFERNAL_TOOLS_IGNORE_HARVEST_LEVEL.get()) {
            return super.isCorrectToolForDrops(stack, state);
        }
        return isCorrectForShears(state);
    }

    private static boolean isCorrectForShears(BlockState state) {
        if (state.is(BlockTags.LEAVES) || state.is(BlockTags.WOOL) || state.is(BlockTags.WOOL_CARPETS)) {
            return true;
        }
        boolean inAnyToolTag = state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_HOE)
                || state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
        return !inAnyToolTag;
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



