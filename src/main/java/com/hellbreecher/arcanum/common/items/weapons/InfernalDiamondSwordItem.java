package com.hellbreecher.arcanum.common.items.weapons;

import com.hellbreecher.arcanum.core.Config;

import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class InfernalDiamondSwordItem extends Item {

    public InfernalDiamondSwordItem(Identifier id) {
        super(new Properties()
                .sword(ArcanumToolMaterials.InfernalDiamondTool, 1.0F, -2.4F)
                .repairable(ArcanumItems.infernaldiamond.get())
                .setId(ResourceKey.create(Registries.ITEM, id)));
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

        enchant(stack, level, Enchantments.FIRE_ASPECT, 5);
        enchant(stack, level, Enchantments.LOOTING, 10);
    }

    private static void enchant(ItemStack stack, Level level, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }
}



