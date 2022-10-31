package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ClientCommandPacket extends PacketBuilder {
    @Nonnull
    private Action action;

    protected ClientCommandPacket(@Nonnull Action action) {
        this.action = action;
    }

    public enum Action {
        PERFORM_RESPAWN, REQUEST_STATS
    }

    @Nonnull
    public static ClientCommandPacket create(@Nonnull Action action) {
        return Mapping.get().packetManager().incoming().clientCommandPacket(action);
    }
}
