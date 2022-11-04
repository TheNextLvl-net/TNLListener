package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerClosePacket extends PacketBuilder {

    private int windowId;

    public static ContainerClosePacket create(int windowId) {
        return Mapping.get().packetManager().outgoing().containerClosePacket(windowId);
    }

    public static ContainerClosePacket create() {
        return create(1);
    }
}
