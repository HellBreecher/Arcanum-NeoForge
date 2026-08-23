package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.ArcanumCommon;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import com.hellbreecher.arcanum.common.loot.SpellLoot;

public final class ArcanumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RegistryPlatform.install(new FabricRegistryFactory());
        MenuTypePlatform.install(new FabricMenuTypeFactory());
        FabricArcanumConfig.install();
        FabricConfigBooleanCondition.register();
        FabricManaAttachments.install();
        ArcanumCommon.initialize();
        LootTableEvents.MODIFY.register((key, table, source, registries) -> {
            var pool = SpellLoot.pool(key.identifier());
            if (source.isBuiltin() && pool != null) table.withPool(pool);
        });
        FabricArcanumHooks.register();
    }
}
