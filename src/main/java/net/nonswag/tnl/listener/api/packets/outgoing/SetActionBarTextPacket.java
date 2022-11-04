package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetActionBarTextPacket extends PacketBuilder {

    private String text;

    public static SetActionBarTextPacket create(String text) {
        return Mapping.get().packetManager().outgoing().setActionBarTextPacket(text);
    }
}
