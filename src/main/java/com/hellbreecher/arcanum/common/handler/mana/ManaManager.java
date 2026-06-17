package com.hellbreecher.arcanum.common.handler.mana;

import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class ManaManager {
    public static final int CRYSTAL_STORAGE_BONUS = 25;
    private static final int REGEN_INTERVAL_TICKS = 20;
    private static final int REGEN_AMOUNT = 50;
    private static final int INFERNAL_ARMOR_REGEN_AMOUNT = 200;
    private static final int ON_FIRE_REGEN_BONUS = 25;

    private ManaManager() {
    }

    public static ManaData get(Player player) {
        return player.getData(ArcanumAttachments.MANA.get());
    }

    public static boolean spend(Player player, int amount) {
        ManaData mana = get(player);
        if (mana.mana() < amount) {
            return false;
        }
        player.setData(ArcanumAttachments.MANA.get(), mana.spendMana(amount));
        return true;
    }

    public static void addStorage(Player player, int amount) {
        player.setData(ArcanumAttachments.MANA.get(), get(player).addStorage(amount));
    }

    public static void addMana(Player player, int amount) {
        player.setData(ArcanumAttachments.MANA.get(), get(player).addMana(amount));
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.tickCount % REGEN_INTERVAL_TICKS != 0 || !hasManaFocus(player)) {
            return;
        }

        ManaData mana = get(player);
        if (mana.mana() < mana.maxMana()) {
            player.setData(ArcanumAttachments.MANA.get(), mana.addMana(regenAmount(player)));
        }
    }

    public static boolean hasSpellbook(Player player) {
        return player.getInventory().contains(ManaManager::isSpellbook);
    }

    public static boolean hasManaFocus(Player player) {
        return player.getInventory().contains(ManaManager::isManaFocus);
    }

    private static boolean isSpellbook(ItemStack stack) {
        return stack.is(ArcanumItems.spellbook.get());
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

    private static boolean isWearingFullInfernalArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanumArmor.infernalhelmet.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanumArmor.infernalchestplate.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanumArmor.infernalleggings.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ArcanumArmor.infernalboots.get());
    }
}
