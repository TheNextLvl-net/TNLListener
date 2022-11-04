package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MoveVehiclePacket extends PacketBuilder {

    private Position position;

    public static MoveVehiclePacket create(Position position) {
        return Mapping.get().packetManager().outgoing().moveVehiclePacket(position);
    }
}
