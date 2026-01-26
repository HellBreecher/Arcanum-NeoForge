package com.hellbreecher.arcanum.common.lib;

import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public final class ArcanumToolMaterials {
    public static final TagKey<Item> GREEN_SAPPHIRE_REPAIR = itemTag("tool_materials/greensapphire");
    public static final TagKey<Item> BLOOD_DIAMOND_REPAIR = itemTag("tool_materials/blooddiamond");
    public static final TagKey<Item> VOID_DIAMOND_REPAIR = itemTag("tool_materials/voiddiamond");
    public static final TagKey<Item> INFERNAL_DIAMOND_REPAIR = itemTag("tool_materials/infernaldiamond");

    public static final ToolMaterial GreenSapphireTool = new ToolMaterial(
            BlockTags.NEEDS_DIAMOND_TOOL,
            1500,
            10.0F,
            10.0F,
            10,
            GREEN_SAPPHIRE_REPAIR
    );

    public static final ToolMaterial BloodDiamondTool = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            3000,
            20.0F,
            50.0F,
            15,
            BLOOD_DIAMOND_REPAIR
    );

    public static final ToolMaterial VoidDiamondTool = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            6000,
            50.0F,
            75.0F,
            18,
            VOID_DIAMOND_REPAIR
    );

    public static final ToolMaterial InfernalDiamondTool = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            8000,
            100.0F,
            100.0F,
            20,
            INFERNAL_DIAMOND_REPAIR
    );

    public static final ToolMaterial InfernalTool = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1,
            500.0F,
            100000.0F,
            30,
            INFERNAL_DIAMOND_REPAIR
    );

    public static final ToolMaterial SapphireBeatingStick = GreenSapphireTool;
    public static final ToolMaterial BloodDiamondBeatingStick = BloodDiamondTool;
    public static final ToolMaterial VoidDiamondBeatingStick = VoidDiamondTool;

    private ArcanumToolMaterials() {}

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }
}
