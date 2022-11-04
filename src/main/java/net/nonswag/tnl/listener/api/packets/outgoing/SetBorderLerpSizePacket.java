package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBorderLerpSizePacket extends PacketBuilder {
    private double oldSize, newSize;
    private long lerpTime;

    public static SetBorderLerpSizePacket create(double oldSize, double newSize, long lerpTime) {
        return Mapping.get().packetManager().outgoing().setBorderLerpSizePacket(oldSize, newSize, lerpTime);
    }
}
