package com.hellbreecher.arcanum.common.handler.mana;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ArcanumAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Reference.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ManaData>> MANA = ATTACHMENTS.register(
            "mana",
            () -> AttachmentType.builder(() -> ManaData.DEFAULT)
                    .serialize(ManaData.CODEC)
                    .copyOnDeath()
                    .sync(ManaData.STREAM_CODEC)
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AuthorMantleData>> AUTHOR_MANTLE = ATTACHMENTS.register(
            "author_mantle",
            () -> AttachmentType.builder(() -> AuthorMantleData.DEFAULT)
                    .serialize(AuthorMantleData.CODEC)
                    .copyOnDeath()
                    .sync(AuthorMantleData.STREAM_CODEC)
                    .build()
    );

    private ArcanumAttachments() {
    }

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
        ManaAccess.install(new ManaAccess.Adapter() {
            public ManaData getMana(net.minecraft.world.entity.player.Player player) { return player.getData(MANA.get()); }
            public void setMana(net.minecraft.world.entity.player.Player player, ManaData value) { player.setData(MANA.get(), value); }
            public AuthorMantleData getAuthorMantle(net.minecraft.world.entity.player.Player player) { return player.getData(AUTHOR_MANTLE.get()); }
            public void setAuthorMantle(net.minecraft.world.entity.player.Player player, AuthorMantleData value) { player.setData(AUTHOR_MANTLE.get(), value); }
        });
    }
}
