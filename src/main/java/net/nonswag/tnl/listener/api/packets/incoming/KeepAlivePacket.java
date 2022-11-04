package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class KeepAlivePacket extends PacketBuilder {
    private long id;

    protected KeepAlivePacket(long id) {
        this.id = id;
    }

    public static KeepAlivePacket create(long id) {
        return Mapping.get().packetManager().incoming().keepAlivePacket(id);
    }
}
