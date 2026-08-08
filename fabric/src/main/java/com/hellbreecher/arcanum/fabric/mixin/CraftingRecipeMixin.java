package com.hellbreecher.arcanum.fabric.mixin;

import com.hellbreecher.arcanum.common.item.DynamicCraftingRemainder;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingRecipe.class)
interface CraftingRecipeMixin {
    @Inject(method = "defaultCraftingReminder", at = @At("RETURN"))
    private static void arcanum$dynamicRemainders(CraftingInput input, CallbackInfoReturnable<NonNullList<ItemStack>> cir) {
        NonNullList<ItemStack> result = cir.getReturnValue();
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.getItem() instanceof DynamicCraftingRemainder dynamic) {
                ItemStackTemplate remainder = dynamic.arcanumCraftingRemainder(stack);
                result.set(slot, remainder == null ? ItemStack.EMPTY : remainder.create());
            }
        }
    }
}
