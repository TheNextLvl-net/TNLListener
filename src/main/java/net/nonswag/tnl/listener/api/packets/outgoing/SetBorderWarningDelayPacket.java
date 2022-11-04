package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBorderWarningDelayPacket extends PacketBuilder {
    private int warningDelay;

    public static SetBorderWarningDelayPacket create(int warningDelay) {
        return Mapping.get().packetManager().outgoing().setBorderWarningDelayPacket(warningDelay);
    }
}
