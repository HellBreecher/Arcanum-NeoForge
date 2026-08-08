package com.hellbreecher.arcanum.common.items.armor;

import com.hellbreecher.arcanum.common.config.ArcanumConfig;

import com.hellbreecher.arcanum.common.items.InfernalManaCosts;
import com.hellbreecher.arcanum.common.lib.ArcanumArmorMaterials;
import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.ArcanumCommon;
import com.hellbreecher.arcanum.core.ArcanumItems;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

public class InfernalArmorItem extends Item {

    private static final Identifier SPRINT_SPEED_MODIFIER_ID =
            Identifier.fromNamespaceAndPath(ArcanumCommon.MOD_ID, "infernal_sprint_speed");

    private static boolean arceffect;

    public InfernalArmorItem(Identifier id, ArmorType type) {
        super(new Item.Properties()
                .humanoidArmor(ArcanumArmorMaterials.INFERNAL, type)
                .repairable(ArcanumItems.infernaldiamond.get())
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public void onCraftedPostProcess(ItemStack stack, Level level) {
        if (!ArcanumConfig.enableCraftedEnchantments()) {
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

    public static void onEquipped(Player player) {
        int inf = Integer.MAX_VALUE;

        if (isWearingFullSet(player)) {
            addAbilities(player);
            if (ArcanumConfig.infernalArmorSetFood()) {
                player.getFoodData().setFoodLevel(20);
            }
            if (ArcanumConfig.infernalArmorFullHeal()) {
                player.setHealth(player.getMaxHealth());
            }
            if (ArcanumConfig.infernalArmorJumpBoost()) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.JUMP_BOOST,
                        inf,
                        ArcanumConfig.infernalArmorJumpBoostAmplifier(),
                        true,
                        false
                ));
            }
            arceffect = true;
        } else if (arceffect) {
            if (!player.isCreative()) {
                removeAbilities(player);
            }
            if (ArcanumConfig.infernalArmorJumpBoost()) {
                player.removeEffect(MobEffects.JUMP_BOOST);
            }
            arceffect = false;
        }
    }

    public static void onPlayerTick(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }

        if (isWearingFullSet(player) && player.isSprinting() && spendPeriodic(player, InfernalManaCosts.SPRINT_TICK)) {
            speed.addOrUpdateTransientModifier(new AttributeModifier(
                    SPRINT_SPEED_MODIFIER_ID,
                    ArcanumConfig.infernalArmorSprintSpeedMultiplier(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        } else {
            speed.removeModifier(SPRINT_SPEED_MODIFIER_ID);
        }

        if (isWearingFullSet(player) && player.getAbilities().flying) {
            spendPeriodic(player, InfernalManaCosts.FLIGHT_TICK);
        }
    }

    public static float onIncomingDamage(Player player, DamageSource source, float amount) {
        if (player.level().isClientSide() || !isWearingFullSet(player)) {
            return amount;
        }

        if (source.is(DamageTypeTags.IS_FIRE) && ManaManager.spend(player, InfernalManaCosts.FIRE_DAMAGE_BLOCK)) {
            player.clearFire();
            return 0.0F;
        }

        if (amount > 0.0F && ManaManager.spend(player, InfernalManaCosts.DAMAGE_REDUCTION)) {
            return amount * 0.75F;
        }
        return amount;
    }

    private static boolean spendPeriodic(Player player, int amount) {
        return player.tickCount % 20 != 0 || ManaManager.spend(player, amount);
    }

    public static boolean isWearingFullSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanumArmor.infernalhelmet.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanumArmor.infernalchestplate.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanumArmor.infernalleggings.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ArcanumArmor.infernalboots.get());
    }

    private static void addAbilities(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            if (ArcanumConfig.infernalArmorEnableFlight()) {
                player.getAbilities().mayfly = true;
                player.getAbilities().setFlyingSpeed(0.15F);
            }
            if (ArcanumConfig.infernalArmorInvulnerable()) {
                player.getAbilities().invulnerable = true;
            }
            player.onUpdateAbilities();
        }
    }

    private static void removeAbilities(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            if (ArcanumConfig.infernalArmorEnableFlight()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().setFlyingSpeed(0.1F);
            }
            if (ArcanumConfig.infernalArmorInvulnerable()) {
                player.getAbilities().invulnerable = false;
            }
            player.onUpdateAbilities();
        }
    }
}



