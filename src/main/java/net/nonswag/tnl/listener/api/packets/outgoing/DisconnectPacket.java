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
public abstract class DisconnectPacket extends PacketBuilder {
    private Component reason;

    public static DisconnectPacket create(Component reason) {
        return Mapping.get().packetManager().outgoing().disconnectPacket(reason);
    }
}
