package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class PongPacket extends PacketBuilder {
    private int id;

    protected PongPacket(int id) {
        this.id = id;
    }

    public static PongPacket create(int id) {
        return Mapping.get().packetManager().incoming().pongPacket(id);
    }
}
