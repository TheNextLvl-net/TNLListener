package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ResourcePackPacket extends PacketBuilder {
    @Nonnull
    private Action action;

    protected ResourcePackPacket(@Nonnull Action action) {
        this.action = action;
    }

    public enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED
    }

    @Nonnull
    public static ResourcePackPacket create(@Nonnull Action action) {
        return Mapping.get().packetManager().incoming().resourcePackPacket(action);
    }
}
