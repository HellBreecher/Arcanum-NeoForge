package com.hellbreecher.arcanum.common.handler.magic;

import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class BindingRitualManager {
    private static final int CODEX_DURATION = 100;
    private static final int GRIMOIRE_DURATION = 1_200;
    private static final int GRIMOIRE_WAVE_INTERVAL = 300;
    private static final double RANGE = 8.0D;
    private static final DustColorTransitionOptions BLOOD_FLAME = new DustColorTransitionOptions(0x4A0008, 0xFF1A1A, 1.25F);
    private static final List<Ritual> RITUALS = new ArrayList<>();

    private BindingRitualManager() { }

    public static boolean begin(ServerLevel level, Player player) {
        ItemEntity binding = findLookedAtBinding(level, player);
        if (binding == null) {
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("Binding requires a binding item on the ground"));
            return false;
        }

        RitualType type = binding.getItem().is(ArcanumItems.grimoire_binding.get()) ? RitualType.GRIMOIRE : RitualType.CODEX;
        if (type == RitualType.GRIMOIRE && level.getDifficulty() == Difficulty.PEACEFUL) {
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("Forbidden binding cannot be performed in Peaceful difficulty"));
            return false;
        }
        ItemStack book = findBook(player, type.input());
        if (book.isEmpty()) {
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal(type == RitualType.GRIMOIRE
                    ? "The Grimoire Binding requires an Arcane Codex"
                    : "The Codex Binding requires a Spellbook"));
            return false;
        }

        ItemEntity ritualBinding = takeOneBinding(level, binding, type.binding());
        ItemStack ritualBookStack = book.copyWithCount(1);
        book.shrink(1);
        ItemEntity ritualBook = new ItemEntity(level, ritualBinding.getX(), ritualBinding.getY(), ritualBinding.getZ(), ritualBookStack);
        prepare(ritualBinding, type.duration());
        prepare(ritualBook, type.duration());
        level.addFreshEntity(ritualBook);

        BlockPos pos = ritualBinding.blockPosition();
        long startedAt = level.getGameTime();
        RITUALS.add(new Ritual(type, level.dimension(), player.getUUID(), ritualBinding.getUUID(), ritualBook.getUUID(), pos, startedAt, startedAt + type.duration()));
        if (type == RitualType.GRIMOIRE) {
            turnToNight(level);
            player.setAbsorptionAmount(0.0F);
            player.setHealth(Math.min(player.getHealth(), 2.0F));
            summonDarkWave(level, player, pos);
            level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.75F, 0.55F);
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("The forbidden binding has begun. Survive for 60 seconds."));
        } else {
            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 0.65F);
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("The binding ritual has begun"));
        }
        return true;
    }

    public static void tick(MinecraftServer server) {
        Iterator<Ritual> iterator = RITUALS.iterator();
        while (iterator.hasNext()) {
            Ritual ritual = iterator.next();
            ServerLevel level = server.getLevel(ritual.dimension());
            if (level == null) continue;
            ItemEntity binding = entity(level, ritual.binding());
            ItemEntity book = entity(level, ritual.book());
            if (binding == null || book == null) {
                if (binding != null) restore(binding);
                if (book != null) restore(book);
                iterator.remove();
                continue;
            }

            long elapsed = level.getGameTime() - ritual.startedAt();
            double progress = Math.clamp((double) elapsed / ritual.type().duration(), 0.0D, 1.0D);
            animate(level, ritual, binding, book, progress);

            if (ritual.type() == RitualType.GRIMOIRE && elapsed > 0 && elapsed % GRIMOIRE_WAVE_INTERVAL == 0) {
                Player owner = server.getPlayerList().getPlayer(ritual.owner());
                summonDarkWave(level, owner != null && owner.level() == level ? owner : null, ritual.pos());
            }
            if (level.getGameTime() < ritual.finishAt()) continue;

            finish(level, ritual, binding, book);
            iterator.remove();
        }
    }

    private static void animate(ServerLevel level, Ritual ritual, ItemEntity binding, ItemEntity book, double progress) {
        Vec3 center = Vec3.atCenterOf(ritual.pos());
        double rotations = ritual.type() == RitualType.GRIMOIRE ? 12.0D : 4.0D;
        double angle = progress * Math.PI * rotations;
        double radius = 0.24D + progress * (ritual.type() == RitualType.GRIMOIRE ? 0.3D : 0.12D);
        double height = ritual.pos().getY() + 0.2D + progress * (ritual.type() == RitualType.GRIMOIRE ? 2.25D : 1.55D);
        binding.setPos(center.x + Math.cos(angle) * radius, height, center.z + Math.sin(angle) * radius);
        book.setPos(center.x + Math.cos(angle + Math.PI) * radius, height, center.z + Math.sin(angle + Math.PI) * radius);
        binding.setYRot((float) Math.toDegrees(angle));
        book.setYRot((float) Math.toDegrees(angle + Math.PI));
        binding.setDeltaMovement(Vec3.ZERO);
        book.setDeltaMovement(Vec3.ZERO);

        if (level.getGameTime() % 5 != 0) return;
        if (ritual.type() == RitualType.GRIMOIRE) {
            level.sendParticles(ParticleTypes.DAMAGE_INDICATOR, center.x, ritual.pos().getY() + 0.15D, center.z, 10, 0.65D, 0.08D, 0.65D, 0.02D);
            level.sendParticles(ParticleTypes.CRIMSON_SPORE, center.x, height, center.z, 10, radius, 0.25D, radius, 0.01D);
            level.sendParticles(BLOOD_FLAME, center.x, height, center.z, 8, radius, 0.2D, radius, 0.02D);
            level.sendParticles(ParticleTypes.SMOKE, center.x, ritual.pos().getY() + 0.15D, center.z, 6, 0.45D, 0.15D, 0.45D, 0.01D);
        } else {
            level.sendParticles(ParticleTypes.FLAME, center.x, ritual.pos().getY() + 0.15D, center.z, 8, 0.3D, 0.2D, 0.3D, 0.015D);
            level.sendParticles(ParticleTypes.ENCHANT, center.x, height, center.z, 5, radius, 0.18D, radius, 0.02D);
        }
    }

    private static void finish(ServerLevel level, Ritual ritual, ItemEntity binding, ItemEntity book) {
        Vec3 center = Vec3.atCenterOf(ritual.pos());
        double height = ritual.pos().getY() + (ritual.type() == RitualType.GRIMOIRE ? 2.45D : 1.75D);
        ItemStack result = new ItemStack(ritual.type().output());
        if (ritual.type() == RitualType.GRIMOIRE && book.getItem().has(DataComponents.CUSTOM_DATA)) {
            result.set(DataComponents.CUSTOM_DATA, book.getItem().get(DataComponents.CUSTOM_DATA));
        }
        binding.discard();
        book.discard();
        ItemEntity resultEntity = new ItemEntity(level, center.x, height, center.z, result);
        resultEntity.setDeltaMovement(0.0D, 0.08D, 0.0D);
        level.addFreshEntity(resultEntity);

        if (ritual.type() == RitualType.GRIMOIRE) {
            level.sendParticles(ParticleTypes.DAMAGE_INDICATOR, center.x, height, center.z, 70, 0.9D, 0.65D, 0.9D, 0.1D);
            level.sendParticles(BLOOD_FLAME, center.x, height, center.z, 90, 0.75D, 0.55D, 0.75D, 0.08D);
            level.sendParticles(ParticleTypes.SMOKE, center.x, height, center.z, 35, 0.6D, 0.4D, 0.6D, 0.04D);
            level.playSound(null, ritual.pos(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 0.55F);
        } else {
            level.sendParticles(ParticleTypes.FLAME, center.x, height, center.z, 60, 0.55D, 0.45D, 0.55D, 0.06D);
            level.sendParticles(ParticleTypes.LAVA, center.x, height, center.z, 12, 0.35D, 0.3D, 0.35D, 0.02D);
            level.sendParticles(ParticleTypes.SMOKE, center.x, ritual.pos().getY() + 0.15D, center.z, 18, 0.35D, 0.2D, 0.35D, 0.01D);
            level.playSound(null, ritual.pos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 0.8F);
        }
    }

    private static void summonDarkWave(ServerLevel level, Player player, BlockPos ritualPos) {
        BlockPos origin = player != null && player.isAlive() ? player.blockPosition() : ritualPos;
        List<String> types = List.of("enderman", "creeper", "skeleton");
        for (int i = 0; i < types.size(); i++) {
            double angle = level.getRandom().nextDouble() * Math.PI * 2.0D + i * Math.PI * 2.0D / types.size();
            int distance = 5 + level.getRandom().nextInt(4);
            BlockPos candidate = origin.offset((int) Math.round(Math.cos(angle) * distance), 0, (int) Math.round(Math.sin(angle) * distance));
            BlockPos spawnPos = findSpawnPosition(level, candidate);
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.withDefaultNamespace(types.get(i)));
            Mob mob = entityType != null && entityType.spawn(level, spawnPos, EntitySpawnReason.TRIGGERED) instanceof Mob spawned ? spawned : null;
            if (mob != null && player != null && player.isAlive()) targetRitualCaster(mob, player);
        }
        Vec3 center = Vec3.atCenterOf(origin);
        level.sendParticles(ParticleTypes.WITCH, center.x, center.y + 0.5D, center.z, 35, 3.5D, 1.0D, 3.5D, 0.04D);
        level.playSound(null, origin, SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, 0.65F);
    }

    private static void turnToNight(ServerLevel level) {
        if (!level.isBrightOutside()) return;
        level.dimensionType().defaultClock().ifPresent(clock ->
                level.clockManager().moveToTimeMarker(clock, ClockTimeMarkers.NIGHT));
    }

    private static void targetRitualCaster(Mob mob, Player player) {
        mob.setTarget(player);
        mob.setAggressive(true);
        if (mob instanceof NeutralMob neutralMob) {
            neutralMob.setPersistentAngerTarget(EntityReference.of(player));
            neutralMob.setTimeToRemainAngry(GRIMOIRE_DURATION + GRIMOIRE_WAVE_INTERVAL);
        }
    }

    private static BlockPos findSpawnPosition(ServerLevel level, BlockPos around) {
        for (int dy = 3; dy >= -3; dy--) {
            BlockPos pos = around.offset(0, dy, 0);
            if (level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir()
                    && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) return pos;
        }
        return around;
    }

    private static ItemEntity takeOneBinding(ServerLevel level, ItemEntity source, Item bindingItem) {
        if (source.getItem().getCount() == 1) return source;
        source.getItem().shrink(1);
        ItemEntity split = new ItemEntity(level, source.getX(), source.getY(), source.getZ(), new ItemStack(bindingItem));
        level.addFreshEntity(split);
        return split;
    }

    private static ItemStack findBook(Player player, Item item) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(item)) return stack;
        }
        return ItemStack.EMPTY;
    }

    private static ItemEntity findLookedAtBinding(ServerLevel level, Player player) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(RANGE));
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(start, end).inflate(1.0D), entity ->
                        (entity.getItem().is(ArcanumItems.codex_binding.get()) || entity.getItem().is(ArcanumItems.grimoire_binding.get()))
                                && RITUALS.stream().noneMatch(ritual -> ritual.binding().equals(entity.getUUID())))
                .stream().filter(entity -> distanceFromRaySqr(start, look, entity.position()) <= 0.8D)
                .min(Comparator.comparingDouble(entity -> entity.distanceToSqr(player))).orElse(null);
    }

    private static double distanceFromRaySqr(Vec3 start, Vec3 direction, Vec3 point) {
        double along = Math.max(0.0D, Math.min(RANGE, point.subtract(start).dot(direction)));
        return point.distanceToSqr(start.add(direction.scale(along)));
    }

    private static void prepare(ItemEntity entity, int duration) {
        entity.setInvulnerable(true);
        entity.setNeverPickUp();
        entity.setNoGravity(true);
        entity.setRemainingFireTicks(duration + 20);
        entity.setDeltaMovement(Vec3.ZERO);
    }

    private static void restore(ItemEntity entity) {
        entity.setInvulnerable(false);
        entity.setNoGravity(false);
        entity.setNoPickUpDelay();
        entity.clearFire();
    }

    private static ItemEntity entity(ServerLevel level, UUID id) {
        return level.getEntity(id) instanceof ItemEntity item ? item : null;
    }

    private enum RitualType {
        CODEX(CODEX_DURATION),
        GRIMOIRE(GRIMOIRE_DURATION);

        private final int duration;

        RitualType(int duration) {
            this.duration = duration;
        }

        int duration() {
            return duration;
        }

        Item input() {
            return this == CODEX ? ArcanumItems.spellbook.get() : ArcanumItems.arcane_codex.get();
        }

        Item binding() {
            return this == CODEX ? ArcanumItems.codex_binding.get() : ArcanumItems.grimoire_binding.get();
        }

        Item output() {
            return this == CODEX ? ArcanumItems.arcane_codex.get() : ArcanumItems.forbidden_grimoire.get();
        }
    }

    private record Ritual(RitualType type, ResourceKey<Level> dimension, UUID owner, UUID binding, UUID book,
                          BlockPos pos, long startedAt, long finishAt) { }
}
