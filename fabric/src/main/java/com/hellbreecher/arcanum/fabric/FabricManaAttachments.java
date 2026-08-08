package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.handler.mana.AuthorMantleData;
import com.hellbreecher.arcanum.common.handler.mana.ManaAccess;
import com.hellbreecher.arcanum.common.handler.mana.ManaData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

final class FabricManaAttachments {
    private static final AttachmentType<ManaData> MANA = AttachmentRegistry.<ManaData>builder()
            .initializer(() -> ManaData.DEFAULT).persistent(ManaData.CODEC.codec()).copyOnDeath()
            .syncWith(ManaData.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
            .buildAndRegister(Identifier.fromNamespaceAndPath("arcanum", "mana"));
    private static final AttachmentType<AuthorMantleData> AUTHOR_MANTLE = AttachmentRegistry.<AuthorMantleData>builder()
            .initializer(() -> AuthorMantleData.DEFAULT).persistent(AuthorMantleData.CODEC.codec()).copyOnDeath()
            .syncWith(AuthorMantleData.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
            .buildAndRegister(Identifier.fromNamespaceAndPath("arcanum", "author_mantle"));

    static void install() {
        ManaAccess.install(new ManaAccess.Adapter() {
            private AttachmentTarget target(Player player) { return (AttachmentTarget) player; }
            public ManaData getMana(Player player) { return target(player).getAttachedOrCreate(MANA); }
            public void setMana(Player player, ManaData value) { target(player).setAttached(MANA, value); }
            public AuthorMantleData getAuthorMantle(Player player) { return target(player).getAttachedOrCreate(AUTHOR_MANTLE); }
            public void setAuthorMantle(Player player, AuthorMantleData value) { target(player).setAttached(AUTHOR_MANTLE, value); }
        });
    }
}
