package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;

import javax.annotation.Nonnull;

@Getter
@Setter
public class MoveVehiclePacket implements IncomingPacket {
    @Nonnull
    private Position position;

    public MoveVehiclePacket(@Nonnull Position position) {
        this.position = position;
    }
}
