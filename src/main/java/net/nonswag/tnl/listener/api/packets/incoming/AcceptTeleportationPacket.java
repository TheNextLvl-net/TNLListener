package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AcceptTeleportationPacket extends PacketBuilder {
    private int id;

    public static AcceptTeleportationPacket create(int id) {
        return Mapping.get().packetManager().incoming().acceptTeleportationPacket(id);
    }
}
