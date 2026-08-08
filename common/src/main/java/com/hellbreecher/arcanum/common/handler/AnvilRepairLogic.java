package com.hellbreecher.arcanum.common.handler;

import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class AnvilRepairLogic {
    private AnvilRepairLogic() { }

    public static Result repair(ItemStack left, ItemStack right) {
        if (left.isEmpty() || right.isEmpty() || !left.isDamageableItem()) return null;
        Item repairItem = repairItem(left);
        if (repairItem == null || !right.is(repairItem) || left.getDamageValue() <= 0) return null;
        int repairPerItem = Math.max(1, left.getMaxDamage() / 4);
        int used = 0;
        int remaining = left.getDamageValue();
        while (remaining > 0 && used < right.getCount()) {
            remaining -= repairPerItem;
            used++;
        }
        ItemStack output = left.copy();
        output.setDamageValue(Math.max(0, remaining));
        return new Result(output, used);
    }

    private static Item repairItem(ItemStack stack) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (id == null) return null;
        String path = id.getPath();
        if (path.contains("greensapphire")) return ArcanumItems.greensapphire.get();
        if (path.contains("blooddiamond")) return ArcanumItems.blooddiamond.get();
        if (path.contains("voiddiamond")) return ArcanumItems.voiddiamond.get();
        if (path.contains("infernaldiamond")) return ArcanumItems.infernaldiamond.get();
        return null;
    }

    public record Result(ItemStack stack, int materialCost) { }
}
