package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TabListPacket extends PacketBuilder {
    private Component header, footer;

    public static TabListPacket create(Component header, Component footer) {
        return Mapping.get().packetManager().outgoing().tabListPacket(header, footer);
    }
}
