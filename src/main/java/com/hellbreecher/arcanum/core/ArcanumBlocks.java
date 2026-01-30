package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.blocks.BaseBlockItem;
import com.hellbreecher.arcanum.common.blocks.BloodDiamondFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.BloodDiamondGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.FermenterBlock;
import com.hellbreecher.arcanum.common.blocks.InfernalGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.InfernalFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.SapphireGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.SapphireFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.VoidDiamondFurnaceBlock;
import com.hellbreecher.arcanum.common.blocks.VoidDiamondGeneratorBlock;
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
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ArcanumBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);

    //Ore Blocks
    public static final DeferredBlock<Block> greensapphireore_block = registerBlock("greensapphireore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(25.0F, 100.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> blooddiamondore_block = registerBlock("blooddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(35.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> voiddiamondore_block = registerBlock("voiddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> vanillarandomore_block = registerBlock("vanillarandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> modrandomore_block = registerBlock("modrandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> deepslategreensapphireore_block = registerBlock("deepslate_greensapphireore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(25.0F, 100.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> deepslateblooddiamondore_block = registerBlock("deepslate_blooddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(35.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> deepslatevoiddiamondore_block = registerBlock("deepslate_voiddiamondore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> deepslatevanillarandomore_block = registerBlock("deepslate_vanillarandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> deepslatemodrandomore_block = registerBlock("deepslate_modrandomore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(4.0F, 200.0F).destroyTime(3f).requiresCorrectToolForDrops());

    //Ore Block Items
    public static final DeferredItem<Item> greensapphireore_block_item = registerBlockItem("greensapphireore", greensapphireore_block);
    public static final DeferredItem<Item> blooddiamondore_block_item = registerBlockItem("blooddiamondore", blooddiamondore_block);
    public static final DeferredItem<Item> voiddiamondore_block_item = registerBlockItem("voiddiamondore", voiddiamondore_block);
    public static final DeferredItem<Item> vanillarandomore_block_item = registerBlockItem("vanillarandomore", vanillarandomore_block);
    public static final DeferredItem<Item> modrandomore_block_item = registerBlockItem("modrandomore", modrandomore_block);
    public static final DeferredItem<Item> deepslategreensapphireore_block_item = registerBlockItem("deepslate_greensapphireore", deepslategreensapphireore_block);
    public static final DeferredItem<Item> deepslateblooddiamondore_block_item = registerBlockItem("deepslate_blooddiamondore", deepslateblooddiamondore_block);
    public static final DeferredItem<Item> deepslatevoiddiamondore_block_item = registerBlockItem("deepslate_voiddiamondore", deepslatevoiddiamondore_block);
    public static final DeferredItem<Item> deepslatevanillarandomore_block_item = registerBlockItem("deepslate_vanillarandomore", deepslatevanillarandomore_block);
    public static final DeferredItem<Item> deepslatemodrandomore_block_item = registerBlockItem("deepslate_modrandomore", deepslatemodrandomore_block);

    //Ingot Blocks
    public static final DeferredBlock<Block> greensapphire_block = registerBlock("greensapphireblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(25.0F, 900.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> blooddiamond_block = registerBlock("blooddiamondblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(35.0F, 180.0F).destroyTime(3f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> voiddiamond_block = registerBlock("voiddiamondblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(45.0F, 360.0F).destroyTime(3f).requiresCorrectToolForDrops());

    //Ingot Block Items
    public static final DeferredItem<Item> greensapphire_block_item = registerBlockItem("greensapphireblock", greensapphire_block);
    public static final DeferredItem<Item> blooddiamond_block_item = registerBlockItem("blooddiamondblock", blooddiamond_block);
    public static final DeferredItem<Item> voiddiamond_block_item = registerBlockItem("voiddiamondblock", voiddiamond_block);

    //Mob Drop Ores
    public static final DeferredBlock<Block> boneore_block = registerBlock("boneore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> fleshore_block = registerBlock("fleshore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> sulfurore_block = registerBlock("sulfurore",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0F, 15.0F).requiresCorrectToolForDrops());

    //Mob Drop Ore Items
    public static final DeferredItem<Item> boneore_block_item = registerBlockItem("boneore", boneore_block);
    public static final DeferredItem<Item> fleshore_block_item = registerBlockItem("fleshore", fleshore_block);
    public static final DeferredItem<Item> sulfurore_block_item = registerBlockItem("sulfurore", sulfurore_block);

    //Mob Drop Blocks
    public static final DeferredBlock<Block> bone_block = registerBlock("boneblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());
    public static final DeferredBlock<Block> flesh_block = registerBlock("fleshblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());
    public static final DeferredBlock<Block> sulfur_block = registerBlock("sulfurblock",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4.0F, 15.0F).instabreak());

    //Mob Drop Block Items
    public static final DeferredItem<Item> bone_block_item = registerBlockItem("boneblock", bone_block);
    public static final DeferredItem<Item> flesh_block_item = registerBlockItem("fleshblock", flesh_block);
    public static final DeferredItem<Item> sulfur_block_item = registerBlockItem("sulfurblock", sulfur_block);

    //Wall & Floor Blocks
    public static final DeferredBlock<TorchBlock> greensapphiretorch_block = registerBlock("greensapphiretorch",
            props -> new TorchBlock(ParticleTypes.FLAME, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(state -> 14).sound(SoundType.WOOD));
    public static final DeferredBlock<WallTorchBlock> wall_greensapphiretorch_block = registerBlock("wall_greensapphiretorch",
            props -> new WallTorchBlock(ParticleTypes.FLAME, props),
            BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(state -> 14)
                    .sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));

    //Standing & Wall Block Items
    public static final DeferredItem<StandingAndWallBlockItem> greensapphiretorch_block_item = ITEMS.register(
            "greensapphiretorch",
            id -> new StandingAndWallBlockItem(
                    greensapphiretorch_block.get(),
                    wall_greensapphiretorch_block.get(),
                    Direction.DOWN,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))
            )
    );

    //Misc Blocks
    public static final DeferredBlock<Block> greensapphirecoal_block = registerBlock("greensapphirecoalblock",
            Block::new,
            BlockBehaviour.Properties.of().strength(20.0F, 50.0F).destroyTime(1f).requiresCorrectToolForDrops());

    //Misc Block Items
    public static final DeferredItem<Item> greensapphirecoal_block_item = registerBlockItem("greensapphirecoalblock", greensapphirecoal_block);

    //Containers

    public static final DeferredBlock<SapphireFurnaceBlock> sapphirefurnace_block = registerBlock("sapphirefurnace",
            SapphireFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(SapphireFurnaceBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<BloodDiamondFurnaceBlock> blooddiamondfurnace_block = registerBlock("blooddiamondfurnace",
            BloodDiamondFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(BloodDiamondFurnaceBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<VoidDiamondFurnaceBlock> voiddiamondfurnace_block = registerBlock("voiddiamondfurnace",
            VoidDiamondFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(VoidDiamondFurnaceBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<InfernalFurnaceBlock> infernalfurnace_block = registerBlock("infernalfurnace",
            InfernalFurnaceBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> state.getValue(InfernalFurnaceBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<FermenterBlock> fermenter_block = registerBlock("fermenter",
            FermenterBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F).lightLevel(state -> 0));
    public static final DeferredBlock<SapphireGeneratorBlock> sapphiregenerator_block = registerBlock("sapphiregenerator",
            SapphireGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(SapphireGeneratorBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<BloodDiamondGeneratorBlock> blooddiamondgenerator_block = registerBlock("blooddiamondgenerator",
            BloodDiamondGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(BloodDiamondGeneratorBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<VoidDiamondGeneratorBlock> voiddiamondgenerator_block = registerBlock("voiddiamondgenerator",
            VoidDiamondGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(VoidDiamondGeneratorBlock.LIT) ? 13 : 0));
    public static final DeferredBlock<InfernalGeneratorBlock> infernalgenerator_block = registerBlock("infernalgenerator",
            InfernalGeneratorBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.5F)
                    .lightLevel(state -> state.getValue(InfernalGeneratorBlock.LIT) ? 13 : 0));

    public static final DeferredItem<Item> sapphirefurnace_block_item = registerBlockItem("sapphirefurnace", sapphirefurnace_block);
    public static final DeferredItem<Item> blooddiamondfurnace_block_item = registerBlockItem("blooddiamondfurnace", blooddiamondfurnace_block);
    public static final DeferredItem<Item> voiddiamondfurnace_block_item = registerBlockItem("voiddiamondfurnace", voiddiamondfurnace_block);
    public static final DeferredItem<Item> infernalfurnace_block_item = registerBlockItem("infernalfurnace", infernalfurnace_block);
    public static final DeferredItem<Item> fermenter_block_item = registerBlockItem("fermenter", fermenter_block);
    public static final DeferredItem<Item> sapphiregenerator_block_item = registerBlockItem("sapphiregenerator", sapphiregenerator_block);
    public static final DeferredItem<Item> blooddiamondgenerator_block_item = registerBlockItem("blooddiamondgenerator", blooddiamondgenerator_block);
    public static final DeferredItem<Item> voiddiamondgenerator_block_item = registerBlockItem("voiddiamondgenerator", voiddiamondgenerator_block);
    public static final DeferredItem<Item> infernalgenerator_block_item = registerBlockItem("infernalgenerator", infernalgenerator_block);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                     BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, id -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id))));
    }

    private static DeferredItem<Item> registerBlockItem(String name, DeferredBlock<? extends Block> block) {
        return ITEMS.register(name, id -> new BaseBlockItem(block.get(), id));
    }
}
