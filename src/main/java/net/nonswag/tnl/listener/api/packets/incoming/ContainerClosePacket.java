package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerClosePacket extends PacketBuilder {
    private int containerId;

    public static ContainerClosePacket create(int containerId) {
        return Mapping.get().packetManager().incoming().containerClosePacket(containerId);
    }
}
