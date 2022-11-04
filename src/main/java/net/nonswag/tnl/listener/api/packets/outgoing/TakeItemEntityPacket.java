package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TakeItemEntityPacket extends PacketBuilder {
    private int entityId, playerId, amount;

    public static TakeItemEntityPacket create(int entityId, int playerId, int amount) {
        return Mapping.get().packetManager().outgoing().takeItemEntityPacket(entityId, playerId, amount);
    }
}
