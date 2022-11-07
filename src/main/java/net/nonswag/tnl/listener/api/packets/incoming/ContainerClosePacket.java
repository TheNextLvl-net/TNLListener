package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ContainerClosePacket extends PacketBuilder {
    private int containerId;

    protected ContainerClosePacket(int containerId) {
        this.containerId = containerId;
    }

    public static ContainerClosePacket create(int containerId) {
        return Mapping.get().packetManager().incoming().containerClosePacket(containerId);
    }
}
