package com.hellbreecher.arcanum;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeSerializers;
import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeTypes;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import com.hellbreecher.arcanum.common.registration.ArcanumEntityTypes;
import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import com.hellbreecher.arcanum.core.*;

/** Loader-neutral Arcanum constants and initialization boundary. */
public final class ArcanumCommon {
    public static final String MOD_ID = "arcanum";
    public static final Logger LOGGER = LogUtils.getLogger();

    private ArcanumCommon() {
    }

    private static boolean initialized;

    public static synchronized void initialize() {
        if (initialized) return;
        initialized = true;
        LOGGER.info("Starting Arcanum common initialization");
        ArcanumBlocks.bootstrap();
        ArcanumItems.bootstrap();
        ArcanumFood.bootstrap();
        ArcanumTools.bootstrap();
        ArcanumWeapons.bootstrap();
        ArcanumArmor.bootstrap();
        ArcanumFeatures.bootstrap();
        ArcanumBlockEntities.bootstrap();
        ArcanumEntityTypes.bootstrap();
        ArcanumMenuTypes.bootstrap();
        ArcanumRecipeSerializers.bootstrap();
        ArcanumRecipeTypes.bootstrap();
        ArcanumCreativeTabs.bootstrap();
    }
}
