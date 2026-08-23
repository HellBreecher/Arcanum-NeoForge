package com.hellbreecher.arcanum.common.handler.mana;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class ManaManager {
    public static final int CRYSTAL_STORAGE_BONUS = 25;
    public static final int AUTHOR_BASE_MAX_MANA = 250;
    private static final int REGEN_INTERVAL_TICKS = 20;
    private static final int REGEN_AMOUNT = 50;
    private static final int INFERNAL_ARMOR_REGEN_AMOUNT = 200;
    private static final int ON_FIRE_REGEN_BONUS = 25;

    private ManaManager() {
    }

    public static ManaData get(Player player) {
        return ManaAccess.getMana(player);
    }

    public static boolean spend(Player player, int amount) {
        ManaData mana = get(player);
        if (mana.mana() < amount) {
            return false;
        }
        ManaAccess.setMana(player, mana.spendMana(amount));
        return true;
    }

    public static void addStorage(Player player, int amount) {
        ManaAccess.setMana(player, get(player).addStorage(amount));
    }

    public static void addMana(Player player, int amount) {
        ManaAccess.setMana(player, get(player).addMana(amount));
    }

    public static void onPlayerTick(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        if (SpellbookItem.isDeveloper(player)) {
            ensureAuthorMana(player);
            sendAuthorAura(player);
        }

        if (player.tickCount % REGEN_INTERVAL_TICKS != 0 || !hasManaFocus(player)) {
            return;
        }

        ManaData mana = get(player);
        if (mana.mana() < mana.maxMana()) {
            ManaAccess.setMana(player, mana.addMana(regenAmount(player)));
        }
    }

    public static boolean hasSpellbook(Player player) {
        return player.getInventory().contains(ManaManager::isSpellbook);
    }

    public static boolean hasManaFocus(Player player) {
        return SpellbookItem.isDeveloper(player) || player.getInventory().contains(ManaManager::isManaFocus);
    }

    private static boolean isSpellbook(ItemStack stack) {
        return SpellbookItem.isSpellBook(stack);
    }

    private static boolean isManaFocus(ItemStack stack) {
        return isSpellbook(stack) || stack.is(ArcanumWeapons.infernalwand.get());
    }

    private static int regenAmount(Player player) {
        int amount = isWearingFullInfernalArmor(player) ? INFERNAL_ARMOR_REGEN_AMOUNT : REGEN_AMOUNT;
        if (player.getRemainingFireTicks() > 0) {
            amount += ON_FIRE_REGEN_BONUS;
        }
        return amount;
    }

    private static void ensureAuthorMana(Player player) {
        ManaData mana = get(player);
        ManaData authorMana = mana.withMinimumMaxMana(AUTHOR_BASE_MAX_MANA);
        if (!authorMana.equals(mana)) {
            ManaAccess.setMana(player, authorMana);
        }
    }

    private static void sendAuthorAura(Player player) {
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        if (isHoldingWand(player)) {
            Vec3 pos = player.position().add(0.0D, 0.8D, 0.0D);
            level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 6, 0.45D, 0.65D, 0.45D, 0.015D);
            level.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 2, 0.25D, 0.35D, 0.25D, 0.005D);
            return;
        }

        if (player.tickCount % 40 == 0) {
            Vec3 pos = player.position().add(0.0D, 0.6D, 0.0D);
            level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 2, 0.25D, 0.35D, 0.25D, 0.01D);
            level.sendParticles(ParticleTypes.LAVA, pos.x, pos.y - 0.2D, pos.z, 1, 0.2D, 0.15D, 0.2D, 0.0D);
        }
    }

    private static boolean isHoldingWand(Player player) {
        return player.getMainHandItem().is(ArcanumWeapons.infernalwand.get())
                || player.getOffhandItem().is(ArcanumWeapons.infernalwand.get());
    }

    private static boolean isWearingFullInfernalArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanumArmor.infernalhelmet.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanumArmor.infernalchestplate.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanumArmor.infernalleggings.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ArcanumArmor.infernalboots.get());
    }
}
