package com.hellbreecher.arcanum.common.registration;

import com.hellbreecher.arcanum.common.entity.RiftSentenceProjectile;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumEntityTypes {
    public static final RegistryRegistrar<EntityType<?>> ENTITY_TYPES = RegistryPlatform.create(BuiltInRegistries.ENTITY_TYPE, Reference.MODID);
    private static final ResourceKey<EntityType<?>> RIFT_SENTENCE_PROJECTILE_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Reference.MODID, "rift_sentence_projectile"));

    public static final RegistryEntry<EntityType<RiftSentenceProjectile>> RIFT_SENTENCE_PROJECTILE =
            ENTITY_TYPES.register("rift_sentence_projectile", () -> EntityType.Builder.<RiftSentenceProjectile>of(RiftSentenceProjectile::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.35F, 0.35F)
                    .clientTrackingRange(4)
                    .updateInterval(2)
                    .build(RIFT_SENTENCE_PROJECTILE_KEY));

    public static void bootstrap() { }
}
