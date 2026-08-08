package com.hellbreecher.arcanum.fabric.mixin;

import com.hellbreecher.arcanum.common.handler.AnvilRepairLogic;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow private int repairItemCountCost;
    @Shadow private DataSlot cost;

    private AnvilMenuMixin() { super(null, 0, null, null, null); }

    @Inject(method = "createResult", at = @At("TAIL"))
    private void arcanum$customRepair(CallbackInfo ci) {
        AnvilRepairLogic.Result repair = AnvilRepairLogic.repair(inputSlots.getItem(0), inputSlots.getItem(1));
        if (repair == null) return;
        resultSlots.setItem(0, repair.stack());
        repairItemCountCost = repair.materialCost();
        cost.set(Math.max(1, cost.get()));
        broadcastChanges();
    }
}
