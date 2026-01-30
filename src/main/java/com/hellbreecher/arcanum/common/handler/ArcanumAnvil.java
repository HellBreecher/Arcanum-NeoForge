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
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.isEmpty() || right.isEmpty() || !left.isDamageableItem()) {
            return;
        }

        Item repairItem = getRepairItem(left);
        if (repairItem == null || !right.is(repairItem)) {
            return;
        }

        int damage = left.getDamageValue();
        if (damage <= 0) {
            return;
        }

        int repairPerItem = Math.max(1, left.getMaxDamage() / 4);
        int available = right.getCount();
        int used = 0;
        int remainingDamage = damage;
        while (remainingDamage > 0 && used < available) {
            remainingDamage -= repairPerItem;
            used++;
        }

        ItemStack output = left.copy();
        output.setDamageValue(Math.max(0, remainingDamage));
        event.setOutput(output);
        event.setMaterialCost(used);
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
