package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.ArcanumCommon;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import net.fabricmc.api.ModInitializer;

public final class ArcanumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RegistryPlatform.install(new FabricRegistryFactory());
        MenuTypePlatform.install(new FabricMenuTypeFactory());
        FabricArcanumConfig.install();
        FabricConfigBooleanCondition.register();
        FabricManaAttachments.install();
        ArcanumCommon.initialize();
        FabricArcanumHooks.register();
    }
}
