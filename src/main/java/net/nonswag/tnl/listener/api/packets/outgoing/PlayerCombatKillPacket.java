package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.*;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerCombatKillPacket extends PacketBuilder {
    private int victimId, killerId;
    private Component message;

    public static PlayerCombatKillPacket create(int victimId, int killerId, Component message) {
        return Mapping.get().packetManager().outgoing().playerCombatKillPacket(victimId, killerId, message);
    }
}
