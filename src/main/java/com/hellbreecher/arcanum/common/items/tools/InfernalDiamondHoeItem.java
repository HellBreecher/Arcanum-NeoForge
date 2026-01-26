package com.hellbreecher.arcanum.common.items.tools;

import com.hellbreecher.arcanum.core.Config;

import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InfernalDiamondHoeItem extends HoeItem {

    public InfernalDiamondHoeItem(Identifier id) {
        super(ArcanumToolMaterials.InfernalDiamondTool, -2.0F, -3.0F, new Properties()
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
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
    }

    private static void enchant(ItemStack stack, Level level, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }
}



