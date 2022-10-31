package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
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
    public static ResourcePackPacket create(@Nonnull String url, @Nullable String hash, @Nullable String prompt, boolean required) {
        return Mapping.get().packetManager().outgoing().resourcePackPacket(url, hash, prompt, required);
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
