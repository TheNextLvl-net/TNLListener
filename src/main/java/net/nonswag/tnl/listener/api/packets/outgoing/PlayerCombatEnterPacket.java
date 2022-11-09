package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerCombatEnterPacket extends PacketBuilder {

    public static PlayerCombatEnterPacket create() {
        return Mapping.get().packetManager().outgoing().playerCombatEnterPacket();
    }
}
