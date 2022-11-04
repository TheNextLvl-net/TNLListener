package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class KeepAlivePacket extends PacketBuilder {

    private long id;

    public static KeepAlivePacket create(long id) {
        return Mapping.get().packetManager().outgoing().keepAlivePacket(id);
    }
}
