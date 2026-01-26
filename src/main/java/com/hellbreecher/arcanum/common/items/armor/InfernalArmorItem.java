package com.hellbreecher.arcanum.common.items.armor;

import com.hellbreecher.arcanum.core.Config;

import com.hellbreecher.arcanum.common.lib.ArcanumArmorMaterials;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.client.Minecraft;
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

public class InfernalArmorItem extends Item {

    private static final Identifier SPRINT_SPEED_MODIFIER_ID =
            Identifier.fromNamespaceAndPath(Arcanum.MODID, "infernal_sprint_speed");

    private static boolean arceffect;

    public InfernalArmorItem(Identifier id, ArmorType type) {
        super(new Item.Properties()
                .humanoidArmor(ArcanumArmorMaterials.INFERNAL, type)
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
        enchant(stack, level, Enchantments.PROTECTION, 5);
        enchant(stack, level, Enchantments.BLAST_PROTECTION, 5);
        enchant(stack, level, Enchantments.THORNS, 5);

        if (stack.is(ArcanumArmor.infernalhelmet.get())) {
            enchant(stack, level, Enchantments.AQUA_AFFINITY, 5);
        } else if (stack.is(ArcanumArmor.infernalchestplate.get())) {
            enchant(stack, level, Enchantments.PROJECTILE_PROTECTION, 5);
        } else if (stack.is(ArcanumArmor.infernalleggings.get())) {
            enchant(stack, level, Enchantments.DEPTH_STRIDER, 5);
        } else if (stack.is(ArcanumArmor.infernalboots.get())) {
            enchant(stack, level, Enchantments.FEATHER_FALLING, 5);
        }
    }

    private static void enchant(ItemStack stack, Level level, ResourceKey<Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }

    public static void onEquipped(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Level level = player.level();
        int inf = Integer.MAX_VALUE;

        if (isWearingFullSet(player)) {
            addAbilities(player);
            if (Config.INFERNAL_ARMOR_SET_FOOD.get()) {
                player.getFoodData().setFoodLevel(20);
            }
            if (Config.INFERNAL_ARMOR_FULL_HEAL.get()) {
                player.setHealth(player.getMaxHealth());
            }
            if (Config.INFERNAL_ARMOR_JUMP_BOOST.get()) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.JUMP_BOOST,
                        inf,
                        Config.INFERNAL_ARMOR_JUMP_BOOST_AMPLIFIER.get(),
                        true,
                        false
                ));
            }
            if (level.isClientSide()) {
                Minecraft instance = Minecraft.getInstance();
            }
            arceffect = true;
        } else if (arceffect) {
            if (!player.isCreative()) {
                removeAbilities(player);
            }
            if (Config.INFERNAL_ARMOR_JUMP_BOOST.get()) {
                player.removeEffect(MobEffects.JUMP_BOOST);
            }
            if (level.isClientSide()) {
                Minecraft instance = Minecraft.getInstance();
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
                    Config.INFERNAL_ARMOR_SPRINT_SPEED_MULTIPLIER.get(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        } else {
            speed.removeModifier(SPRINT_SPEED_MODIFIER_ID);
        }
    }

    private static boolean isWearingFullSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanumArmor.infernalhelmet.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanumArmor.infernalchestplate.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanumArmor.infernalleggings.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ArcanumArmor.infernalboots.get());
    }

    private static void addAbilities(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            if (Config.INFERNAL_ARMOR_ENABLE_FLIGHT.get()) {
                player.getAbilities().mayfly = true;
                player.getAbilities().setFlyingSpeed(0.15F);
            }
            if (Config.INFERNAL_ARMOR_INVULNERABLE.get()) {
                player.getAbilities().invulnerable = true;
            }
            player.onUpdateAbilities();
        }
    }

    private static void removeAbilities(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            if (Config.INFERNAL_ARMOR_ENABLE_FLIGHT.get()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().setFlyingSpeed(0.1F);
            }
            if (Config.INFERNAL_ARMOR_INVULNERABLE.get()) {
                player.getAbilities().invulnerable = false;
            }
            player.onUpdateAbilities();
        }
    }
}



