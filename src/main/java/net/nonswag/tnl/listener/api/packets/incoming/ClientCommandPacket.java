package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ClientCommandPacket extends PacketBuilder {
    private Action action;

    public enum Action {
        PERFORM_RESPAWN, REQUEST_STATS
    }

    public static ClientCommandPacket create(Action action) {
        return Mapping.get().packetManager().incoming().clientCommandPacket(action);
    }
}
