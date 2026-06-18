package com.hellbreecher.arcanum.common.entity;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.registration.ArcanumEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RiftSentenceProjectile extends ThrowableProjectile {
    private static final int MAX_LIFETIME = 80;
    private static final PowerParticleOption DRAGON_BREATH = PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F);

    public RiftSentenceProjectile(EntityType<? extends RiftSentenceProjectile> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
    }

    public RiftSentenceProjectile(Level level, LivingEntity owner) {
        this(ArcanumEntityTypes.RIFT_SENTENCE_PROJECTILE.get(), level);
        setOwner(owner);
        Vec3 look = owner.getLookAngle().normalize();
        setPos(owner.getX() + look.x * 1.2D, owner.getEyeY() - 0.1D + look.y * 1.2D, owner.getZ() + look.z * 1.2D);
        shoot(look.x, look.y, look.z, 1.9F, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        spawnTrailParticles();
        if (!level().isClientSide() && tickCount > MAX_LIFETIME) {
            discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Entity owner = getOwner();
        Entity target = result.getEntity();
        if (!(owner instanceof Player caster) || target == caster || !SpellbookItem.isDeveloper(caster)) {
            failedImpact(serverLevel, result.getLocation());
            discard();
            return;
        }

        SpellbookItem.teleportEntityByRiftSentence(serverLevel, target, caster, true);
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (level() instanceof ServerLevel serverLevel) {
            failedImpact(serverLevel, result.getLocation());
        }
        discard();
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.MISS) {
            return;
        }
        super.onHit(result);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return target != getOwner() && super.canHitEntity(target);
    }

    private void spawnTrailParticles() {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(DRAGON_BREATH, getX(), getY(), getZ(), 2, 0.08D, 0.08D, 0.08D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.PORTAL, getX(), getY(), getZ(), 4, 0.12D, 0.12D, 0.12D, 0.02D);
            serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 1, 0.05D, 0.05D, 0.05D, 0.0D);
            if (tickCount % 5 == 0) {
                serverLevel.sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 1, 0.06D, 0.06D, 0.06D, 0.0D);
            }
        } else {
            level().addParticle(ParticleTypes.PORTAL, getX(), getY(), getZ(), random.nextGaussian() * 0.04D, random.nextGaussian() * 0.04D, random.nextGaussian() * 0.04D);
        }
    }

    private static void failedImpact(ServerLevel level, Vec3 pos) {
        level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, 24, 0.35D, 0.35D, 0.35D, 0.04D);
        level.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 12, 0.2D, 0.2D, 0.2D, 0.01D);
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.PLAYERS, 0.7F, 1.5F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }
}
