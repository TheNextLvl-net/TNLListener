package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ClientCommandPacket implements IncomingPacket {
    @Nonnull
    private Action action;

    public ClientCommandPacket(@Nonnull Action action) {
        this.action = action;
    }

    public enum Action {
        PERFORM_RESPAWN, REQUEST_STATS
    }
}
