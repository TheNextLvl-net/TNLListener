package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResourcePackPacket extends PacketBuilder {

    private String url;
    @Nullable
    private String hash;
    @Nullable
    private Component prompt;
    private boolean required;

    public static ResourcePackPacket create(String url, @Nullable String hash, @Nullable Component prompt, boolean required) {
        return Mapping.get().packetManager().outgoing().resourcePackPacket(url, hash, prompt, required);
    }

    public static ResourcePackPacket create(String url, @Nullable String hash, @Nullable Component prompt) {
        return create(url, hash, prompt, false);
    }

    public static ResourcePackPacket create(String url, @Nullable String hash) {
        return create(url, hash, null);
    }

    public static ResourcePackPacket create(String url) {
        return create(url, null);
    }
}
