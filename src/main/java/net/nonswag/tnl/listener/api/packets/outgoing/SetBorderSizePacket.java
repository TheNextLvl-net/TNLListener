package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBorderSizePacket extends PacketBuilder {
    private double size;

    public static SetBorderSizePacket create(double size) {
        return Mapping.get().packetManager().outgoing().setBorderSizePacket(size);
    }
}
