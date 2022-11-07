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
public abstract class SetActionBarTextPacket extends PacketBuilder {
    private Component text;

    public static SetActionBarTextPacket create(Component text) {
        return Mapping.get().packetManager().outgoing().setActionBarTextPacket(text);
    }
}
