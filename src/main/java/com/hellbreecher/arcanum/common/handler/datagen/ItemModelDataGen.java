package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.core.Arcanum;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumFood;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumTools;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public final class ItemModelDataGen extends ModelProvider {
    public ItemModelDataGen(PackOutput output) {
        super(output, Arcanum.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ArcanumBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(
                ArcanumItems.ITEMS.getEntries().stream(),
                ArcanumFood.FOODS.getEntries().stream(),
                ArcanumBlocks.ITEMS.getEntries().stream(),
                ArcanumTools.ITEMS.getEntries().stream(),
                ArcanumWeapons.ITEMS.getEntries().stream(),
                ArcanumArmor.ITEMS.getEntries().stream()
        ).flatMap(stream -> stream);
    }

    private static void registerItemModels(ItemModelGenerators itemModels) {

        //Ingots
        itemModels.generateFlatItem(ArcanumItems.greensapphire.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.blooddiamond.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.voiddiamond.get(), ModelTemplates.FLAT_ITEM);
        //Magic
        itemModels.generateFlatItem(ArcanumItems.infernaldiamond.get(), ModelTemplates.FLAT_ITEM);
        //Misc
        itemModels.generateFlatItem(ArcanumItems.quartzstick.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.blooddiamondstick.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.emptycan.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.redcup.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumItems.mountaindewmix.get(), ModelTemplates.FLAT_ITEM);
        //Crafting
        itemModels.generateFlatItem(ArcanumItems.hammer.get(), ModelTemplates.FLAT_ITEM);
        //Fuel
        itemModels.generateFlatItem(ArcanumItems.greensapphirecoal.get(), ModelTemplates.FLAT_ITEM);
        //Tools
        itemModels.generateFlatItem(ArcanumTools.greensapphirepickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.blooddiamondpickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.voiddiamondpickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernaldiamondpickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernalpickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.greensapphireaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.blooddiamondaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.voiddiamondaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernaldiamondaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernalaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.greensapphireshovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.blooddiamondshovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.voiddiamondshovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernaldiamondshovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernalshovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.greensapphirehoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.blooddiamondhoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.voiddiamondhoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernaldiamondhoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernalhoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.greensapphireshears.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.blooddiamondshears.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.voiddiamondshears.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernaldiamondshears.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.infernalshears.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumTools.arcwrench.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        //Weapons
        itemModels.generateFlatItem(ArcanumWeapons.greensapphiresword.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.blooddiamondsword.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.voiddiamondsword.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.infernaldiamondsword.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.infernalsword.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.sapphirebeatingstick.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.blooddiamondbeatingstick.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.voiddiamondbeatingstick.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.infernalbeatingstick.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ArcanumWeapons.infernalwand.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        //Armor
        itemModels.generateFlatItem(ArcanumArmor.greensapphirehelmet.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.greensapphirechestplate.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.greensapphireleggings.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.greensapphireboots.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.blooddiamondhelmet.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.blooddiamondchestplate.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.blooddiamondleggings.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.blooddiamondboots.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.voiddiamondhelmet.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.voiddiamondchestplate.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.voiddiamondleggings.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.voiddiamondboots.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernaldiamondhelmet.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernaldiamondchestplate.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernaldiamondleggings.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernaldiamondboots.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernalhelmet.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernalchestplate.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernalleggings.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumArmor.infernalboots.get(), ModelTemplates.FLAT_ITEM);
        //Food
        itemModels.generateFlatItem(ArcanumFood.crushedapple.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.toast.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.cortonwine.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.unfermentedbeer.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.fermentedbeer.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.applecider.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.warmapplecider.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ArcanumFood.mountaindew.get(), ModelTemplates.FLAT_ITEM);
    }

    private static void registerBlockModels(BlockModelGenerators blockModels) {
        registerCubeWithItem(blockModels, ArcanumBlocks.greensapphireore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.blooddiamondore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.voiddiamondore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.vanillarandomore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.modrandomore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.deepslategreensapphireore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.deepslateblooddiamondore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.deepslatevoiddiamondore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.deepslatevanillarandomore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.deepslatemodrandomore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.greensapphire_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.blooddiamond_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.voiddiamond_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.boneore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.fleshore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.sulfurore_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.bone_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.flesh_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.sulfur_block.get());
        registerCubeWithItem(blockModels, ArcanumBlocks.greensapphirecoal_block.get());
        blockModels.createNormalTorch(
                ArcanumBlocks.greensapphiretorch_block.get(),
                ArcanumBlocks.wall_greensapphiretorch_block.get()
        );
    }

    private static void registerCubeWithItem(BlockModelGenerators blockModels, Block block) {
        blockModels.createTrivialCube(block);
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }
}
