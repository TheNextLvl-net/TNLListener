package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ResourcePackPacket implements IncomingPacket {
    @Nonnull
    private Action action;

    public ResourcePackPacket(@Nonnull Action action) {
        this.action = action;
    }

    public enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED
    }
}
