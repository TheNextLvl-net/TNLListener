package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class MoveVehiclePacket extends PacketBuilder {
    private Position position;

    protected MoveVehiclePacket(Position position) {
        this.position = position;
    }

    public static MoveVehiclePacket create(Position position) {
        return Mapping.get().packetManager().incoming().moveVehiclePacket(position);
    }
}
