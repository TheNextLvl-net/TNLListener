package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PingPacket extends PacketBuilder {
    private int id;

    public static PingPacket create(int id) {
        return Mapping.get().packetManager().outgoing().pingPacket(id);
    }
}
