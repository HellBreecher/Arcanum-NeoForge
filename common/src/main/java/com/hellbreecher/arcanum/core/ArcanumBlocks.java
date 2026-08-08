package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.blocks.BaseBlockItem;
import com.hellbreecher.arcanum.common.blocks.ArcanumTorchBlock;
import com.hellbreecher.arcanum.common.blocks.ArcanumWallTorchBlock;
import com.hellbreecher.arcanum.common.blocks.BloodDiamondFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.BloodDiamondGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.FermenterBlock;
import com.hellbreecher.arcanum.common.blocks.InfernalCrystalPlantBlock;
import com.hellbreecher.arcanum.common.blocks.InfernalGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.InfernalFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.SapphireGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.SapphireFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.VoidDiamondFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.VoidDiamondGeneratorBlock;
import com.hellbreecher.arcanum.common.items.InfernalCrystalBlockItem;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.material.PushReaction;

import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Function;

public class ArcanumBlocks {
    public static final RegistryRegistrar<Block> BLOCKS = RegistryPlatform.create(BuiltInRegistries.BLOCK, Reference.MODID);
    public static final RegistryRegistrar<Item> ITEMS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    //Ore Blocks
    public static final RegistryEntry<Block> greensapphireore_block = registerBlock("greensapphireore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(25.0F, 100.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> blooddiamondore_block = registerBlock("blooddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(35.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> voiddiamondore_block = registerBlock("voiddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> vanillarandomore_block = registerBlock("vanillarandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> modrandomore_block = registerBlock("modrandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());

    public static final RegistryEntry<Block> deepslategreensapphireore_block = registerBlock("deepslate_greensapphireore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(25.0F, 100.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> deepslateblooddiamondore_block = registerBlock("deepslate_blooddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(35.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> deepslatevoiddiamondore_block = registerBlock("deepslate_voiddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> deepslatevanillarandomore_block = registerBlock("deepslate_vanillarandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> deepslatemodrandomore_block = registerBlock("deepslate_modrandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());

    //Ore Block Items
    public static final RegistryEntry<Item> greensapphireore_block_item = registerBlockItem("greensapphireore", greensapphireore_block);
    public static final RegistryEntry<Item> blooddiamondore_block_item = registerBlockItem("blooddiamondore", blooddiamondore_block);
    public static final RegistryEntry<Item> voiddiamondore_block_item = registerBlockItem("voiddiamondore", voiddiamondore_block);
    public static final RegistryEntry<Item> vanillarandomore_block_item = registerBlockItem("vanillarandomore", vanillarandomore_block);
    public static final RegistryEntry<Item> modrandomore_block_item = registerBlockItem("modrandomore", modrandomore_block);
    public static final RegistryEntry<Item> deepslategreensapphireore_block_item = registerBlockItem("deepslate_greensapphireore", deepslategreensapphireore_block);
    public static final RegistryEntry<Item> deepslateblooddiamondore_block_item = registerBlockItem("deepslate_blooddiamondore", deepslateblooddiamondore_block);
    public static final RegistryEntry<Item> deepslatevoiddiamondore_block_item = registerBlockItem("deepslate_voiddiamondore", deepslatevoiddiamondore_block);
    public static final RegistryEntry<Item> deepslatevanillarandomore_block_item = registerBlockItem("deepslate_vanillarandomore", deepslatevanillarandomore_block);
    public static final RegistryEntry<Item> deepslatemodrandomore_block_item = registerBlockItem("deepslate_modrandomore", deepslatemodrandomore_block);

    //Ingot Blocks
    public static final RegistryEntry<Block> greensapphire_block = registerBlock("greensapphireblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(25.0F, 900.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> blooddiamond_block = registerBlock("blooddiamondblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(35.0F, 180.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> voiddiamond_block = registerBlock("voiddiamondblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> infernalcrystal_block = registerBlock("infernalcrystalblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).strength(1.5F, 6.0F).destroyTime(1.5F)
                    .lightLevel(state -> 7).requiresCorrectToolForDrops());
    public static final RegistryEntry<InfernalCrystalPlantBlock> infernalcrystal_plant = registerBlock("infernalcrystal",
            InfernalCrystalPlantBlock::new,
            BlockBehaviour.Properties.of().noCollision().noOcclusion().instabreak().sound(SoundType.AMETHYST)
                    .lightLevel(state -> 10).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY));

    //Ingot Block Items
    public static final RegistryEntry<Item> greensapphire_block_item = registerBlockItem("greensapphireblock", greensapphire_block);
    public static final RegistryEntry<Item> blooddiamond_block_item = registerBlockItem("blooddiamondblock", blooddiamond_block);
    public static final RegistryEntry<Item> voiddiamond_block_item = registerBlockItem("voiddiamondblock", voiddiamond_block);
    public static final RegistryEntry<Item> infernalcrystal_block_item = ITEMS.register("infernalcrystalblock",
            id -> new InfernalCrystalBlockItem(infernalcrystal_block.get(), id));

    //Mob Drop Ores
    public static final RegistryEntry<Block> boneore_block = registerBlock("boneore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> fleshore_block = registerBlock("fleshore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());
    public static final RegistryEntry<Block> sulfurore_block = registerBlock("sulfurore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());

    //Mob Drop Ore Items
    public static final RegistryEntry<Item> boneore_block_item = registerBlockItem("boneore", boneore_block);
    public static final RegistryEntry<Item> fleshore_block_item = registerBlockItem("fleshore", fleshore_block);
    public static final RegistryEntry<Item> sulfurore_block_item = registerBlockItem("sulfurore", sulfurore_block);

    //Mob Drop Blocks
    public static final RegistryEntry<Block> bone_block = registerBlock("boneblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());
    public static final RegistryEntry<Block> flesh_block = registerBlock("fleshblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());
    public static final RegistryEntry<Block> sulfur_block = registerBlock("sulfurblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());

    //Mob Drop Block Items
    public static final RegistryEntry<Item> bone_block_item = registerBlockItem("boneblock", bone_block);
    public static final RegistryEntry<Item> flesh_block_item = registerBlockItem("fleshblock", flesh_block);
    public static final RegistryEntry<Item> sulfur_block_item = registerBlockItem("sulfurblock", sulfur_block);

    //Wall & Floor Blocks
    public static final RegistryEntry<TorchBlock> greensapphiretorch_block = registerBlock("greensapphiretorch",
            props -> new ArcanumTorchBlock(ParticleTypes.FLAME, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(state -> 15).sound(SoundType.WOOD));
    public static final RegistryEntry<WallTorchBlock> wall_greensapphiretorch_block = registerBlock("wall_greensapphiretorch",
            props -> new ArcanumWallTorchBlock(ParticleTypes.FLAME, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(state -> 15)
                    .sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));

    //Standing & Wall Block Items
    public static final RegistryEntry<StandingAndWallBlockItem> greensapphiretorch_block_item = ITEMS.register(
            "greensapphiretorch",
            id -> new StandingAndWallBlockItem(
                    greensapphiretorch_block.get(),
                    wall_greensapphiretorch_block.get(),
                    Direction.DOWN,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))
            )
    );

    //Misc Blocks
    public static final RegistryEntry<Block> greensapphirecoal_block = registerBlock("greensapphirecoalblock",
            Block::new,
            BlockBehaviour.Properties.of().strength(20.0F, 50.0F).destroyTime(1f).requiresCorrectToolForDrops());

    //Misc Block Items
    public static final RegistryEntry<Item> greensapphirecoal_block_item = registerBlockItem("greensapphirecoalblock", greensapphirecoal_block);

    //Containers

    public static final RegistryEntry<SapphireFurnaceBlock> sapphirefurnace_block = registerBlock("sapphirefurnace",
            SapphireFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(SapphireFurnaceBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<BloodDiamondFurnaceBlock> blooddiamondfurnace_block = registerBlock("blooddiamondfurnace",
            BloodDiamondFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(BloodDiamondFurnaceBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<VoidDiamondFurnaceBlock> voiddiamondfurnace_block = registerBlock("voiddiamondfurnace",
            VoidDiamondFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(VoidDiamondFurnaceBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<InfernalFurnaceBlock> infernalfurnace_block = registerBlock("infernalfurnace",
            InfernalFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(InfernalFurnaceBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<FermenterBlock> fermenter_block = registerBlock("fermenter",
            FermenterBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> 0));
    public static final RegistryEntry<SapphireGeneratorBlock> sapphiregenerator_block = registerBlock("sapphiregenerator",
            SapphireGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(SapphireGeneratorBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<BloodDiamondGeneratorBlock> blooddiamondgenerator_block = registerBlock("blooddiamondgenerator",
            BloodDiamondGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(BloodDiamondGeneratorBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<VoidDiamondGeneratorBlock> voiddiamondgenerator_block = registerBlock("voiddiamondgenerator",
            VoidDiamondGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(VoidDiamondGeneratorBlock.LIT) ? 13 : 0));
    public static final RegistryEntry<InfernalGeneratorBlock> infernalgenerator_block = registerBlock("infernalgenerator",
            InfernalGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(InfernalGeneratorBlock.LIT) ? 13 : 0));

    public static final RegistryEntry<Item> sapphirefurnace_block_item = registerBlockItem("sapphirefurnace", sapphirefurnace_block);
    public static final RegistryEntry<Item> blooddiamondfurnace_block_item = registerBlockItem("blooddiamondfurnace", blooddiamondfurnace_block);
    public static final RegistryEntry<Item> voiddiamondfurnace_block_item = registerBlockItem("voiddiamondfurnace", voiddiamondfurnace_block);
    public static final RegistryEntry<Item> infernalfurnace_block_item = registerBlockItem("infernalfurnace", infernalfurnace_block);
    public static final RegistryEntry<Item> fermenter_block_item = registerBlockItem("fermenter", fermenter_block);
    public static final RegistryEntry<Item> sapphiregenerator_block_item = registerBlockItem("sapphiregenerator", sapphiregenerator_block);
    public static final RegistryEntry<Item> blooddiamondgenerator_block_item = registerBlockItem("blooddiamondgenerator", blooddiamondgenerator_block);
    public static final RegistryEntry<Item> voiddiamondgenerator_block_item = registerBlockItem("voiddiamondgenerator", voiddiamondgenerator_block);
    public static final RegistryEntry<Item> infernalgenerator_block_item = registerBlockItem("infernalgenerator", infernalgenerator_block);

    public static void bootstrap() { }

    private static <T extends Block> RegistryEntry<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                     BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, id -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id))));
    }

    private static RegistryEntry<Item> registerBlockItem(String name, RegistryEntry<? extends Block> block) {
        return ITEMS.register(name, id -> new BaseBlockItem(block.get(), id));
    }
}
