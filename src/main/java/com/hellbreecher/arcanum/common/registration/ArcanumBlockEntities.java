package com.hellbreecher.arcanum.common.registration;

import com.hellbreecher.arcanum.common.blocks.tileentities.BloodDiamondFurnaceBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.BloodDiamondGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.FermenterBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.InfernalGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.InfernalFurnaceBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.SapphireGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.SapphireFurnaceBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.VoidDiamondGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.VoidDiamondFurnaceBlockEntity;
import com.hellbreecher.arcanum.common.lib.Reference;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ArcanumBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SapphireFurnaceBlockEntity>> SAPPHIRE_FURNACE = BLOCK_ENTITIES.register("sapphirefurnace",
            () -> new BlockEntityType<>(SapphireFurnaceBlockEntity::new, ArcanumBlocks.sapphirefurnace_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BloodDiamondFurnaceBlockEntity>> BLOOD_DIAMOND_FURNACE = BLOCK_ENTITIES.register("blooddiamondfurnace",
            () -> new BlockEntityType<>(BloodDiamondFurnaceBlockEntity::new, ArcanumBlocks.blooddiamondfurnace_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidDiamondFurnaceBlockEntity>> VOID_DIAMOND_FURNACE = BLOCK_ENTITIES.register("voiddiamondfurnace",
            () -> new BlockEntityType<>(VoidDiamondFurnaceBlockEntity::new, ArcanumBlocks.voiddiamondfurnace_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfernalFurnaceBlockEntity>> INFERNAL_FURNACE = BLOCK_ENTITIES.register("infernalfurnace",
            () -> new BlockEntityType<>(InfernalFurnaceBlockEntity::new, ArcanumBlocks.infernalfurnace_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITIES.register("fermenter",
            () -> new BlockEntityType<>(FermenterBlockEntity::new, ArcanumBlocks.fermenter_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SapphireGeneratorBlockEntity>> SAPPHIRE_GENERATOR = BLOCK_ENTITIES.register("sapphiregenerator",
            () -> new BlockEntityType<>(SapphireGeneratorBlockEntity::new, ArcanumBlocks.sapphiregenerator_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BloodDiamondGeneratorBlockEntity>> BLOOD_DIAMOND_GENERATOR = BLOCK_ENTITIES.register("blooddiamondgenerator",
            () -> new BlockEntityType<>(BloodDiamondGeneratorBlockEntity::new, ArcanumBlocks.blooddiamondgenerator_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidDiamondGeneratorBlockEntity>> VOID_DIAMOND_GENERATOR = BLOCK_ENTITIES.register("voiddiamondgenerator",
            () -> new BlockEntityType<>(VoidDiamondGeneratorBlockEntity::new, ArcanumBlocks.voiddiamondgenerator_block.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfernalGeneratorBlockEntity>> INFERNAL_GENERATOR = BLOCK_ENTITIES.register("infernalgenerator",
            () -> new BlockEntityType<>(InfernalGeneratorBlockEntity::new, ArcanumBlocks.infernalgenerator_block.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
