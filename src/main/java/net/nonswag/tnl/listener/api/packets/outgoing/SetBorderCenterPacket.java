package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBorderCenterPacket extends PacketBuilder {

    private VirtualBorder.Center center;

    public static SetBorderCenterPacket create(VirtualBorder.Center center) {
        return Mapping.get().packetManager().outgoing().setBorderCenterPacket(center);
    }
}
