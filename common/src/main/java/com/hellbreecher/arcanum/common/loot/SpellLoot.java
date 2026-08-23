package com.hellbreecher.arcanum.common.loot;

import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public final class SpellLoot {
    private static final Set<String> CHESTS = Set.of(
            "chests/simple_dungeon", "chests/abandoned_mineshaft", "chests/desert_pyramid",
            "chests/jungle_temple", "chests/woodland_mansion", "chests/stronghold_library",
            "chests/bastion_treasure", "chests/ancient_city", "chests/end_city_treasure"
    );

    private SpellLoot() { }

    public static LootPool.Builder pool(Identifier table) {
        if (!table.getNamespace().equals("minecraft") || !CHESTS.contains(table.getPath())) return null;

        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(EmptyLootItem.emptyItem().setWeight(7))
                .add(page(ArcanumItems.renew_page.get()))
                .add(page(ArcanumItems.ember_ward_page.get()))
                .add(page(ArcanumItems.blink_page.get()))
                .add(page(ArcanumItems.infernal_chains_page.get()))
                .add(page(ArcanumItems.soulflare_page.get()))
                .add(page(ArcanumItems.ash_step_page.get()))
                .add(page(ArcanumItems.crystal_ward_page.get()))
                .add(page(ArcanumItems.dispel_page.get()));

        if (isHighTier(table.getPath())) {
            pool.add(LootItem.lootTableItem(ArcanumItems.codex_binding.get()).setWeight(2));
        }
        if (table.getPath().equals("chests/end_city_treasure")) {
            pool.add(LootItem.lootTableItem(ArcanumItems.grimoire_binding.get()).setWeight(1));
            pool.add(page(ArcanumItems.aetherwing_page.get()));
            pool.add(page(ArcanumItems.blood_lance_page.get()));
            pool.add(page(ArcanumItems.crimson_feast_page.get()));
            pool.add(page(ArcanumItems.sanguine_ward_page.get()));
        }
        return pool;
    }

    private static LootItem.Builder<?> page(Item item) {
        return LootItem.lootTableItem(item).setWeight(2);
    }

    private static boolean isHighTier(String path) {
        return path.equals("chests/stronghold_library") || path.equals("chests/woodland_mansion")
                || path.equals("chests/bastion_treasure") || path.equals("chests/ancient_city")
                || path.equals("chests/end_city_treasure");
    }
}
