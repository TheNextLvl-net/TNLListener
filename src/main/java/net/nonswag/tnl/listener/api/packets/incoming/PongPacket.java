package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PongPacket extends PacketBuilder {
    private int id;

    public static PongPacket create(int id) {
        return Mapping.get().packetManager().incoming().pongPacket(id);
    }
}
