package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetBorderLerpSizePacket extends PacketBuilder {

    private double oldSize, newSize;
    private long lerpTime;

    protected SetBorderLerpSizePacket(double oldSize, double newSize, long lerpTime) {
        this.oldSize = oldSize;
        this.newSize = newSize;
        this.lerpTime = lerpTime;
    }

    @Nonnull
    public static SetBorderLerpSizePacket create(double oldSize, double newSize, long lerpTime) {
        return Mapping.get().packetManager().outgoing().setBorderLerpSizePacket(oldSize, newSize, lerpTime);
    }
}
