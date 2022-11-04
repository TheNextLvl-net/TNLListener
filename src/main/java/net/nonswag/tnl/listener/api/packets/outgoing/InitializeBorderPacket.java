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
public abstract class InitializeBorderPacket extends PacketBuilder {

    private VirtualBorder border;

    public static InitializeBorderPacket create(VirtualBorder virtualBorder) {
        return Mapping.get().packetManager().outgoing().initializeBorderPacket(virtualBorder);
    }
}
