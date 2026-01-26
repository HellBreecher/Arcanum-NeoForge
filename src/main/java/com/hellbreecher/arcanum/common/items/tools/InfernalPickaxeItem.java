package com.hellbreecher.arcanum.common.items.tools;

import com.hellbreecher.arcanum.core.Config;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;

public class InfernalPickaxeItem extends Item {

    public InfernalPickaxeItem(Identifier id) {
        super(new Item.Properties()
                .pickaxe(ArcanumToolMaterials.InfernalTool, 1.0F, -2.8F)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (!Config.INFERNAL_TOOLS_IGNORE_HARVEST_LEVEL.get()) {
            return super.isCorrectToolForDrops(stack, state);
        }
        return isCorrectForTool(state, BlockTags.MINEABLE_WITH_PICKAXE);
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

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        if (!Config.INFERNAL_AUTOSMELT_ENABLED.get()) {
            return;
        }

        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof InfernalPickaxeItem)) {
            return;
        }

        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        if (state.isAir()) {
            return;
        }

        event.setCanceled(true);

        if (!player.getAbilities().instabuild) {
            int dropMultiplier = Config.INFERNAL_AUTOSMELT_DROP_MULTIPLIER.get();
            int xpMultiplier = Config.INFERNAL_AUTOSMELT_XP_MULTIPLIER.get();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            List<ItemStack> drops = Block.getDrops(state, level, pos, blockEntity, player, tool);
            for (ItemStack drop : drops) {
                ItemStack smelted = smeltDrop(level, drop, dropMultiplier);
                Block.popResource(level, pos, smelted);
            }
            if (state.getBlock() instanceof DropExperienceBlock dropBlock) {
                int xp = dropBlock.getExpDrop(state, level, pos, blockEntity, player, tool) * xpMultiplier;
                dropBlock.popExperience(level, pos, xp);
            }
            tool.hurtAndBreak(1, player, InteractionHand.MAIN_HAND);
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    private static ItemStack smeltDrop(ServerLevel level, ItemStack drop, int dropMultiplier) {
        if (drop.isEmpty()) {
            return drop;
        }

        ItemStack result = findSmeltedResult(level, drop);
        if (result.isEmpty()) {
            return drop;
        }

        result.setCount(result.getCount() * drop.getCount() * dropMultiplier);
        return result;
    }

    private static ItemStack findSmeltedResult(ServerLevel level, ItemStack drop) {
        ItemStack single = drop.copyWithCount(1);
        SingleRecipeInput input = new SingleRecipeInput(single);

        Optional<RecipeHolder<SmeltingRecipe>> smelting = level.recipeAccess()
                .getRecipeFor(RecipeType.SMELTING, input, level);
        if (smelting.isPresent()) {
            return smelting.get().value().assemble(input, level.registryAccess());
        }

        Optional<RecipeHolder<BlastingRecipe>> blasting = level.recipeAccess()
                .getRecipeFor(RecipeType.BLASTING, input, level);
        if (blasting.isPresent()) {
            return blasting.get().value().assemble(input, level.registryAccess());
        }

        return ItemStack.EMPTY;
    }
}



