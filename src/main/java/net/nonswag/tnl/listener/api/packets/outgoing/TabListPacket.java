package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TabListPacket extends PacketBuilder {
    private String header, footer;

    public static TabListPacket create(String header, String footer) {
        return Mapping.get().packetManager().outgoing().tabListPacket(header, footer);
    }
}
