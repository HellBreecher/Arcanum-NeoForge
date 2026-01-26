package com.hellbreecher.arcanum.core;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Arcanum.MODID)
public class ArcanumCreativeTabs  {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "arcanum" namespace
    public static final DeferredRegister<CreativeModeTab> ARCANUMTABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Arcanum.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ARCANUM_TAB = ARCANUMTABS.register("arcanum_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.arcanum")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ArcanumItems.infernaldiamond.toStack())
            .displayItems((parameters, output) -> {
                //Block Ores
                output.accept(ArcanumBlocks.greensapphireore_block.get());
                output.accept(ArcanumBlocks.blooddiamondore_block.get());
                output.accept(ArcanumBlocks.voiddiamondore_block.get());
                output.accept(ArcanumBlocks.vanillarandomore_block.get());
                output.accept(ArcanumBlocks.modrandomore_block.get());
                output.accept(ArcanumBlocks.deepslategreensapphireore_block.get());
                output.accept(ArcanumBlocks.deepslateblooddiamondore_block.get());
                output.accept(ArcanumBlocks.deepslatevoiddiamondore_block.get());
                output.accept(ArcanumBlocks.deepslatevanillarandomore_block.get());
                output.accept(ArcanumBlocks.deepslatemodrandomore_block.get());
                //Block Ingot
                output.accept(ArcanumBlocks.greensapphire_block.get());
                output.accept(ArcanumBlocks.blooddiamond_block.get());
                output.accept(ArcanumBlocks.voiddiamond_block.get());
                //Block Mob Drop
                output.accept(ArcanumBlocks.boneore_block.get());
                output.accept(ArcanumBlocks.fleshore_block.get());
                output.accept(ArcanumBlocks.sulfurore_block.get());
                output.accept(ArcanumBlocks.bone_block.get());
                output.accept(ArcanumBlocks.flesh_block.get());
                output.accept(ArcanumBlocks.sulfur_block.get());
                //Block Fuel
                output.accept(ArcanumBlocks.greensapphirecoal_block.get());
                //Block Wall & Floor
                output.accept(ArcanumBlocks.greensapphiretorch_block.get());
                //Item Food
                output.accept(ArcanumFood.crushedapple.get());
                output.accept(ArcanumFood.toast.get());
                output.accept(ArcanumFood.cortonwine.get());
                output.accept(ArcanumFood.unfermentedbeer.get());
                output.accept(ArcanumFood.fermentedbeer.get());
                output.accept(ArcanumFood.applecider.get());
                output.accept(ArcanumFood.warmapplecider.get());
                output.accept(ArcanumFood.mountaindew.get());
                //Item Ingots
                output.accept(ArcanumItems.greensapphire.get());
                output.accept(ArcanumItems.blooddiamond.get());
                output.accept(ArcanumItems.voiddiamond.get());
                //Item Magical Ingots
                output.accept(ArcanumItems.infernaldiamond.get());
                //Item Misc
                output.accept(ArcanumItems.quartzstick.get());
                output.accept(ArcanumItems.blooddiamondstick.get());
                output.accept(ArcanumItems.emptycan.get());
                output.accept(ArcanumItems.redcup.get());
                output.accept(ArcanumItems.mountaindewmix.get());
                //Item Crafting
                output.accept(ArcanumItems.hammer.get());
                //Item Fuels
                output.accept(ArcanumItems.greensapphirecoal.get());
                //Tools
                output.accept(ArcanumTools.greensapphirepickaxe.get());
                output.accept(ArcanumTools.blooddiamondpickaxe.get());
                output.accept(ArcanumTools.voiddiamondpickaxe.get());
                output.accept(ArcanumTools.infernaldiamondpickaxe.get());
                output.accept(ArcanumTools.infernalpickaxe.get());
                output.accept(ArcanumTools.greensapphireaxe.get());
                output.accept(ArcanumTools.blooddiamondaxe.get());
                output.accept(ArcanumTools.voiddiamondaxe.get());
                output.accept(ArcanumTools.infernaldiamondaxe.get());
                output.accept(ArcanumTools.infernalaxe.get());
                output.accept(ArcanumTools.greensapphireshovel.get());
                output.accept(ArcanumTools.blooddiamondshovel.get());
                output.accept(ArcanumTools.voiddiamondshovel.get());
                output.accept(ArcanumTools.infernaldiamondshovel.get());
                output.accept(ArcanumTools.infernalshovel.get());
                output.accept(ArcanumTools.greensapphirehoe.get());
                output.accept(ArcanumTools.blooddiamondhoe.get());
                output.accept(ArcanumTools.voiddiamondhoe.get());
                output.accept(ArcanumTools.infernaldiamondhoe.get());
                output.accept(ArcanumTools.infernalhoe.get());
                output.accept(ArcanumTools.greensapphireshears.get());
                output.accept(ArcanumTools.blooddiamondshears.get());
                output.accept(ArcanumTools.voiddiamondshears.get());
                output.accept(ArcanumTools.infernaldiamondshears.get());
                output.accept(ArcanumTools.infernalshears.get());
                output.accept(ArcanumTools.arcwrench.get());
                //Weapons
                output.accept(ArcanumWeapons.greensapphiresword.get());
                output.accept(ArcanumWeapons.blooddiamondsword.get());
                output.accept(ArcanumWeapons.voiddiamondsword.get());
                output.accept(ArcanumWeapons.infernaldiamondsword.get());
                output.accept(ArcanumWeapons.infernalsword.get());
                output.accept(ArcanumWeapons.sapphirebeatingstick.get());
                output.accept(ArcanumWeapons.blooddiamondbeatingstick.get());
                output.accept(ArcanumWeapons.voiddiamondbeatingstick.get());
                output.accept(ArcanumWeapons.infernalbeatingstick.get());
                output.accept(ArcanumWeapons.infernalwand.get());
                //Armor
                output.accept(ArcanumArmor.greensapphirehelmet.get());
                output.accept(ArcanumArmor.greensapphirechestplate.get());
                output.accept(ArcanumArmor.greensapphireleggings.get());
                output.accept(ArcanumArmor.greensapphireboots.get());
                output.accept(ArcanumArmor.blooddiamondhelmet.get());
                output.accept(ArcanumArmor.blooddiamondchestplate.get());
                output.accept(ArcanumArmor.blooddiamondleggings.get());
                output.accept(ArcanumArmor.blooddiamondboots.get());
                output.accept(ArcanumArmor.voiddiamondhelmet.get());
                output.accept(ArcanumArmor.voiddiamondchestplate.get());
                output.accept(ArcanumArmor.voiddiamondleggings.get());
                output.accept(ArcanumArmor.voiddiamondboots.get());
                output.accept(ArcanumArmor.infernaldiamondhelmet.get());
                output.accept(ArcanumArmor.infernaldiamondchestplate.get());
                output.accept(ArcanumArmor.infernaldiamondleggings.get());
                output.accept(ArcanumArmor.infernaldiamondboots.get());
                output.accept(ArcanumArmor.infernalhelmet.get());
                output.accept(ArcanumArmor.infernalchestplate.get());
                output.accept(ArcanumArmor.infernalleggings.get());
                output.accept(ArcanumArmor.infernalboots.get());


            }).build());

    public static void register(IEventBus eventBus) {
        ARCANUMTABS.register(eventBus);
    }
}
