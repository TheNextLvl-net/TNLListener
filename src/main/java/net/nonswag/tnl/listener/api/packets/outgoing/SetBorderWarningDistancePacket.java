package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetBorderWarningDistancePacket extends PacketBuilder {

    private int warningDistance;

    protected SetBorderWarningDistancePacket(int warningDistance) {
        this.warningDistance = warningDistance;
    }

    @Nonnull
    public static SetBorderWarningDistancePacket create(int warningDistance) {
        return Mapping.get().packetManager().outgoing().setBorderWarningDistancePacket(warningDistance);
    }
}
