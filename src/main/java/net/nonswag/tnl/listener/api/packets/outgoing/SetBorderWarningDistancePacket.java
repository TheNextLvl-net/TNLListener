package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBorderWarningDistancePacket extends PacketBuilder {
    private int warningDistance;

    public static SetBorderWarningDistancePacket create(int warningDistance) {
        return Mapping.get().packetManager().outgoing().setBorderWarningDistancePacket(warningDistance);
    }
}
