package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class ResourcePackPacket extends PacketBuilder {

    @Nonnull
    private String url;
    @Nullable
    private String hash, prompt;
    private boolean required;

    protected ResourcePackPacket(@Nonnull String url, @Nullable String hash, @Nullable String prompt, boolean required) {
        this.url = url;
        this.hash = hash;
        this.prompt = prompt;
        this.required = required;
    }

    @Nonnull
    public ResourcePackPacket setUrl(@Nonnull String url) {
        this.url = url;
        return this;
    }

    @Nonnull
    public ResourcePackPacket setHash(@Nullable String hash) {
        this.hash = hash;
        return this;
    }

    @Nonnull
    public ResourcePackPacket setPrompt(@Nullable String prompt) {
        this.prompt = prompt;
        return this;
    }

    @Nonnull
    public ResourcePackPacket setRequired(boolean required) {
        this.required = required;
        return this;
    }

    @Nonnull
    public static ResourcePackPacket create(@Nonnull String url, @Nullable String hash, @Nullable String prompt, boolean required) {
        return Mapping.get().packets().resourcePackPacket(url, hash, prompt, required);
    }

    @Nonnull
    public static ResourcePackPacket create(@Nonnull String url, @Nullable String hash, @Nullable String prompt) {
        return create(url, hash, prompt, false);
    }

    @Nonnull
    public static ResourcePackPacket create(@Nonnull String url, @Nullable String hash) {
        return create(url, hash, null);
    }

    @Nonnull
    public static ResourcePackPacket create(@Nonnull String url) {
        return create(url, null);
    }
}
