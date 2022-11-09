package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.*;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerCombatEndPacket extends PacketBuilder {
    private int durationSinceLastAttack, killerId;

    public static PlayerCombatEndPacket create(int durationSinceLastAttack, int killerId) {
        return Mapping.get().packetManager().outgoing().playerCombatEndPacket(durationSinceLastAttack, killerId);
    }
}
