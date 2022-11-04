package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ClientCommandPacket extends PacketBuilder {
    private Action action;

    protected ClientCommandPacket(Action action) {
        this.action = action;
    }

    public enum Action {
        PERFORM_RESPAWN, REQUEST_STATS
    }

    public static ClientCommandPacket create(Action action) {
        return Mapping.get().packetManager().incoming().clientCommandPacket(action);
    }
}
