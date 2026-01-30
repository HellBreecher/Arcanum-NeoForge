package com.hellbreecher.arcanum.common.blocks.tileentities;

import com.hellbreecher.arcanum.common.blocks.FermenterBlock;
import com.hellbreecher.arcanum.common.blocks.container.FermenterMenu;
import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeTypes;
import com.hellbreecher.arcanum.common.recipe.FermentingRecipe;
import com.hellbreecher.arcanum.common.recipe.FermentingRecipeInput;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FermenterBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final int INGREDIENT_SLOT_START = 0;
    private static final int INGREDIENT_SLOT_END = 2;
    private static final int BASE_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;
    private static final int[] SLOTS_FOR_UP = new int[]{OUTPUT_SLOT};
    private static final int[] SLOTS_FOR_DOWN = new int[]{OUTPUT_SLOT};
    private static final int[] SLOTS_FOR_SIDES = new int[]{INGREDIENT_SLOT_START, INGREDIENT_SLOT_START + 1, INGREDIENT_SLOT_START + 2, BASE_SLOT};
    private static final int DATA_BREW_TIME = 0;
    private static final int DATA_BREW_TIME_TOTAL = 1;
    private static final int DATA_COUNT = 2;

    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int brewTime;
    private int brewTimeTotal;
    private Item baseItem;
    private Item ingredientItem1;
    private Item ingredientItem2;
    private Item ingredientItem3;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_BREW_TIME -> FermenterBlockEntity.this.brewTime;
                case DATA_BREW_TIME_TOTAL -> FermenterBlockEntity.this.brewTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_BREW_TIME -> FermenterBlockEntity.this.brewTime = value;
                case DATA_BREW_TIME_TOTAL -> FermenterBlockEntity.this.brewTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public FermenterBlockEntity(BlockPos pos, BlockState state) {
        super(ArcanumBlockEntities.FERMENTER.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.arcanum.fermenter");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new FermenterMenu(id, player, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FermenterBlockEntity blockEntity) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        boolean canFerment = canFerment(serverLevel, blockEntity);
        boolean wasBrewing = blockEntity.brewTime > 0;
        if (wasBrewing) {
            blockEntity.brewTime--;
            boolean done = blockEntity.brewTime == 0;
            if (done && canFerment) {
                doFerment(serverLevel, pos, blockEntity);
            } else if (!canFerment
                    || blockEntity.items.get(INGREDIENT_SLOT_START).isEmpty()
                    || blockEntity.items.get(INGREDIENT_SLOT_START + 1).isEmpty()
                    || blockEntity.items.get(INGREDIENT_SLOT_START + 2).isEmpty()
                    || blockEntity.items.get(BASE_SLOT).isEmpty()
                    || blockEntity.items.get(BASE_SLOT).getItem() != blockEntity.baseItem
                    || blockEntity.items.get(INGREDIENT_SLOT_START).getItem() != blockEntity.ingredientItem1
                    || blockEntity.items.get(INGREDIENT_SLOT_START + 1).getItem() != blockEntity.ingredientItem2
                    || blockEntity.items.get(INGREDIENT_SLOT_START + 2).getItem() != blockEntity.ingredientItem3) {
                blockEntity.brewTime = 0;
            }
            setChanged(level, pos, state);
        } else if (canFerment) {
            FermentingRecipe recipe = firstRecipe(serverLevel, blockEntity);
            if (recipe != null) {
                blockEntity.brewTime = recipe.fermentTime();
                blockEntity.brewTimeTotal = recipe.fermentTime();
                blockEntity.baseItem = blockEntity.items.get(BASE_SLOT).getItem();
                blockEntity.ingredientItem1 = blockEntity.items.get(INGREDIENT_SLOT_START).getItem();
                blockEntity.ingredientItem2 = blockEntity.items.get(INGREDIENT_SLOT_START + 1).getItem();
                blockEntity.ingredientItem3 = blockEntity.items.get(INGREDIENT_SLOT_START + 2).getItem();
                setChanged(level, pos, state);
            }
        } else if (blockEntity.brewTimeTotal != 0) {
            blockEntity.brewTimeTotal = 0;
            setChanged(level, pos, state);
        }

        boolean lit = blockEntity.brewTime > 0;
        if (state.getValue(FermenterBlock.LIT) != lit) {
            level.setBlock(pos, state.setValue(FermenterBlock.LIT, lit), 3);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        }
        return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.brewTime = input.getShortOr("BrewTime", (short) 0);
        this.brewTimeTotal = input.getShortOr("BrewTimeTotal", (short) 0);
        if (this.brewTime > 0
                && !this.items.get(BASE_SLOT).isEmpty()
                && !this.items.get(INGREDIENT_SLOT_START).isEmpty()
                && !this.items.get(INGREDIENT_SLOT_START + 1).isEmpty()
                && !this.items.get(INGREDIENT_SLOT_START + 2).isEmpty()) {
            this.baseItem = this.items.get(BASE_SLOT).getItem();
            this.ingredientItem1 = this.items.get(INGREDIENT_SLOT_START).getItem();
            this.ingredientItem2 = this.items.get(INGREDIENT_SLOT_START + 1).getItem();
            this.ingredientItem3 = this.items.get(INGREDIENT_SLOT_START + 2).getItem();
        }
    }

    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putShort("BrewTime", (short) this.brewTime);
        output.putShort("BrewTimeTotal", (short) this.brewTimeTotal);
        ContainerHelper.saveAllItems(output, this.items);
    }

    private static boolean canFerment(ServerLevel level, FermenterBlockEntity blockEntity) {
        ItemStack ingredient1 = blockEntity.items.get(INGREDIENT_SLOT_START);
        ItemStack ingredient2 = blockEntity.items.get(INGREDIENT_SLOT_START + 1);
        ItemStack ingredient3 = blockEntity.items.get(INGREDIENT_SLOT_START + 2);
        ItemStack base = blockEntity.items.get(BASE_SLOT);
        ItemStack output = blockEntity.items.get(OUTPUT_SLOT);
        if (ingredient1.isEmpty() || ingredient2.isEmpty() || ingredient3.isEmpty()) {
            return false;
        }
        if (base.isEmpty()) {
            return false;
        }
        FermentingRecipeInput input = new FermentingRecipeInput(base, ingredient1, ingredient2, ingredient3);
        return level.recipeAccess()
                .getRecipeFor(ArcanumRecipeTypes.FERMENTING.get(), input, level)
                .map(holder -> holder.value())
                .map(recipe -> recipe.assemble(input, level.registryAccess()))
                .filter(result -> canAcceptOutput(output, result))
                .isPresent();
    }

    private static FermentingRecipe firstRecipe(ServerLevel level, FermenterBlockEntity blockEntity) {
        ItemStack ingredient1 = blockEntity.items.get(INGREDIENT_SLOT_START);
        ItemStack ingredient2 = blockEntity.items.get(INGREDIENT_SLOT_START + 1);
        ItemStack ingredient3 = blockEntity.items.get(INGREDIENT_SLOT_START + 2);
        ItemStack base = blockEntity.items.get(BASE_SLOT);
        if (base.isEmpty()) {
            return null;
        }
        FermentingRecipeInput input = new FermentingRecipeInput(base, ingredient1, ingredient2, ingredient3);
        return level.recipeAccess()
                .getRecipeFor(ArcanumRecipeTypes.FERMENTING.get(), input, level)
                .map(holder -> holder.value())
                .orElse(null);
    }

    private static void doFerment(ServerLevel level, BlockPos pos, FermenterBlockEntity blockEntity) {
        ItemStack ingredient1 = blockEntity.items.get(INGREDIENT_SLOT_START);
        ItemStack ingredient2 = blockEntity.items.get(INGREDIENT_SLOT_START + 1);
        ItemStack ingredient3 = blockEntity.items.get(INGREDIENT_SLOT_START + 2);
        ItemStack base = blockEntity.items.get(BASE_SLOT);
        ItemStack output = blockEntity.items.get(OUTPUT_SLOT);
        if (ingredient1.isEmpty() || ingredient2.isEmpty() || ingredient3.isEmpty()) {
            return;
        }
        if (base.isEmpty()) {
            return;
        }
        FermentingRecipeInput input = new FermentingRecipeInput(base, ingredient1, ingredient2, ingredient3);
        FermentingRecipe recipe = level.recipeAccess()
                .getRecipeFor(ArcanumRecipeTypes.FERMENTING.get(), input, level)
                .map(holder -> holder.value())
                .orElse(null);
        if (recipe != null) {
            ItemStack result = recipe.assemble(input, level.registryAccess());
            if (canAcceptOutput(output, result)) {
                if (output.isEmpty()) {
                    blockEntity.items.set(OUTPUT_SLOT, result);
                } else {
                    output.grow(result.getCount());
                }
                consumeIngredient(level, pos, blockEntity, BASE_SLOT);
                consumeIngredient(level, pos, blockEntity, INGREDIENT_SLOT_START);
                consumeIngredient(level, pos, blockEntity, INGREDIENT_SLOT_START + 1);
                consumeIngredient(level, pos, blockEntity, INGREDIENT_SLOT_START + 2);
                setChanged(level, pos, blockEntity.getBlockState());
            }
        }
    }

    private static void consumeIngredient(Level level, BlockPos pos, FermenterBlockEntity blockEntity, int slot) {
        ItemStack stack = blockEntity.items.get(slot);
        if (stack.isEmpty()) {
            return;
        }
        ItemStack remainder = stack.getCraftingRemainder();
        stack.shrink(1);
        if (!remainder.isEmpty()) {
            if (stack.isEmpty()) {
                stack = remainder;
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder);
            }
        }
        blockEntity.items.set(slot, stack);
    }

    private static boolean canAcceptOutput(ItemStack output, ItemStack result) {
        if (result.isEmpty()) {
            return false;
        }
        if (output.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameComponents(output, result)) {
            return false;
        }
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }
}
