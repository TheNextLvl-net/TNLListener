package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class WindowClosePacket extends PacketBuilder {
    private int containerId;

    protected WindowClosePacket(int containerId) {
        this.containerId = containerId;
    }

    public static WindowClosePacket create(int containerId) {
        return Mapping.get().packetManager().incoming().windowClosePacket(containerId);
    }
}
