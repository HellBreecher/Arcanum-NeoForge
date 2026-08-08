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
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.Set;

public class ArcanumBlockEntities {
    public static final RegistryRegistrar<BlockEntityType<?>> BLOCK_ENTITIES = RegistryPlatform.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Reference.MODID);

    public static final RegistryEntry<BlockEntityType<SapphireFurnaceBlockEntity>> SAPPHIRE_FURNACE = BLOCK_ENTITIES.register("sapphirefurnace",
            () -> new BlockEntityType<>(SapphireFurnaceBlockEntity::new, Set.of(ArcanumBlocks.sapphirefurnace_block.get())));
    public static final RegistryEntry<BlockEntityType<BloodDiamondFurnaceBlockEntity>> BLOOD_DIAMOND_FURNACE = BLOCK_ENTITIES.register("blooddiamondfurnace",
            () -> new BlockEntityType<>(BloodDiamondFurnaceBlockEntity::new, Set.of(ArcanumBlocks.blooddiamondfurnace_block.get())));
    public static final RegistryEntry<BlockEntityType<VoidDiamondFurnaceBlockEntity>> VOID_DIAMOND_FURNACE = BLOCK_ENTITIES.register("voiddiamondfurnace",
            () -> new BlockEntityType<>(VoidDiamondFurnaceBlockEntity::new, Set.of(ArcanumBlocks.voiddiamondfurnace_block.get())));
    public static final RegistryEntry<BlockEntityType<InfernalFurnaceBlockEntity>> INFERNAL_FURNACE = BLOCK_ENTITIES.register("infernalfurnace",
            () -> new BlockEntityType<>(InfernalFurnaceBlockEntity::new, Set.of(ArcanumBlocks.infernalfurnace_block.get())));
    public static final RegistryEntry<BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITIES.register("fermenter",
            () -> new BlockEntityType<>(FermenterBlockEntity::new, Set.of(ArcanumBlocks.fermenter_block.get())));
    public static final RegistryEntry<BlockEntityType<SapphireGeneratorBlockEntity>> SAPPHIRE_GENERATOR = BLOCK_ENTITIES.register("sapphiregenerator",
            () -> new BlockEntityType<>(SapphireGeneratorBlockEntity::new, Set.of(ArcanumBlocks.sapphiregenerator_block.get())));
    public static final RegistryEntry<BlockEntityType<BloodDiamondGeneratorBlockEntity>> BLOOD_DIAMOND_GENERATOR = BLOCK_ENTITIES.register("blooddiamondgenerator",
            () -> new BlockEntityType<>(BloodDiamondGeneratorBlockEntity::new, Set.of(ArcanumBlocks.blooddiamondgenerator_block.get())));
    public static final RegistryEntry<BlockEntityType<VoidDiamondGeneratorBlockEntity>> VOID_DIAMOND_GENERATOR = BLOCK_ENTITIES.register("voiddiamondgenerator",
            () -> new BlockEntityType<>(VoidDiamondGeneratorBlockEntity::new, Set.of(ArcanumBlocks.voiddiamondgenerator_block.get())));
    public static final RegistryEntry<BlockEntityType<InfernalGeneratorBlockEntity>> INFERNAL_GENERATOR = BLOCK_ENTITIES.register("infernalgenerator",
            () -> new BlockEntityType<>(InfernalGeneratorBlockEntity::new, Set.of(ArcanumBlocks.infernalgenerator_block.get())));

    public static void bootstrap() { }
}
