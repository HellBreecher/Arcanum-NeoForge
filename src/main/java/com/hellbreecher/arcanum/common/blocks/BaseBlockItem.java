package com.hellbreecher.arcanum.common.blocks;

import com.hellbreecher.arcanum.core.ArcanumBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.FuelValues;

public class BaseBlockItem extends BlockItem {

	public BaseBlockItem(Block blockIn, Identifier id) {
		super(blockIn, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)));
	}

	@Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType, FuelValues fuelValues) {
        if (itemStack.getItem() == ArcanumBlocks.greensapphirecoal_block_item.get()) {
            return 57600;
        }
        else return 0;
    }
	
}
