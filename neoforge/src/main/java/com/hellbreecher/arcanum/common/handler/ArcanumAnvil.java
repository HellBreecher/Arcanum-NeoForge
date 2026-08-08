package com.hellbreecher.arcanum.common.handler;

import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public final class ArcanumAnvil {
    private ArcanumAnvil() {}

    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        AnvilRepairLogic.Result result = AnvilRepairLogic.repair(event.getLeft(), event.getRight());
        if (result == null) return;
        event.setOutput(result.stack());
        event.setMaterialCost(result.materialCost());
    }

    private static Item getRepairItem(ItemStack stack) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (id == null) {
            return null;
        }

        String path = id.getPath();
        if (path.contains("greensapphire")) {
            return ArcanumItems.greensapphire.get();
        }
        if (path.contains("blooddiamond")) {
            return ArcanumItems.blooddiamond.get();
        }
        if (path.contains("voiddiamond")) {
            return ArcanumItems.voiddiamond.get();
        }
        if (path.contains("infernaldiamond")) {
            return ArcanumItems.infernaldiamond.get();
        }

        return null;
    }
}
