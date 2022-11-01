package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class MoveVehiclePacket extends PacketBuilder {

    @Nonnull
    private Position position;

    protected MoveVehiclePacket(@Nonnull Position position) {
        this.position = position;
    }

    @Nonnull
    public static MoveVehiclePacket create(@Nonnull Position position) {
        return Mapping.get().packetManager().outgoing().moveVehiclePacket(position);
    }
}
