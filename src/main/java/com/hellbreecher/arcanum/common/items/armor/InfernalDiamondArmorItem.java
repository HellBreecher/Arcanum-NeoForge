package com.hellbreecher.arcanum.common.items.armor;

import com.hellbreecher.arcanum.core.Config;

import com.hellbreecher.arcanum.common.lib.ArcanumArmorMaterials;
import com.hellbreecher.arcanum.core.Arcanum;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class InfernalDiamondArmorItem extends Item {

    private static final Identifier SPRINT_SPEED_MODIFIER_ID =
            Identifier.fromNamespaceAndPath(Arcanum.MODID, "infernal_diamond_sprint_speed");

    private static boolean arceffect;

    public InfernalDiamondArmorItem(Identifier id, ArmorType type) {
        super(new Item.Properties()
                .humanoidArmor(ArcanumArmorMaterials.INFERNAL_DIAMOND, type)
                .setId(ResourceKey.create(Registries.ITEM, id)));
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

        enchant(stack, level, Enchantments.FIRE_ASPECT, 5);
        enchant(stack, level, Enchantments.FIRE_PROTECTION, 5);
    }

    private static void enchant(ItemStack stack, Level level, ResourceKey<Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }
    
    public static void onArmorEquipped(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        int inf = Integer.MAX_VALUE;
        if (isWearingFullSet(player)) {
            if (Config.INFERNAL_DIAMOND_ARMOR_FIRE_RESIST.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, inf, 1, true, false));
            }
            if (Config.INFERNAL_DIAMOND_ARMOR_JUMP_BOOST.get()) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.JUMP_BOOST,
                        inf,
                        Config.INFERNAL_DIAMOND_ARMOR_JUMP_BOOST_AMPLIFIER.get(),
                        true,
                        false
                ));
            }
            arceffect = true;
        } else if (arceffect) {
            if (Config.INFERNAL_DIAMOND_ARMOR_FIRE_RESIST.get()) {
                player.removeEffect(MobEffects.FIRE_RESISTANCE);
            }
            if (Config.INFERNAL_DIAMOND_ARMOR_JUMP_BOOST.get()) {
                player.removeEffect(MobEffects.JUMP_BOOST);
            }
            arceffect = false;
        }
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }

        if (isWearingFullSet(player) && player.isSprinting()) {
            speed.addOrUpdateTransientModifier(new AttributeModifier(
                    SPRINT_SPEED_MODIFIER_ID,
                    Config.INFERNAL_DIAMOND_ARMOR_SPRINT_SPEED_MULTIPLIER.get(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        } else {
            speed.removeModifier(SPRINT_SPEED_MODIFIER_ID);
        }
    }

    private static boolean isWearingFullSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanumArmor.infernaldiamondhelmet.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanumArmor.infernaldiamondchestplate.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanumArmor.infernaldiamondleggings.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ArcanumArmor.infernaldiamondboots.get());
    }
}



