package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class TakeItemEntityPacket extends PacketBuilder {
    private int entityId, playerId, amount;

    protected TakeItemEntityPacket(int entityId, int playerId, int amount) {
        this.entityId = entityId;
        this.playerId = playerId;
        this.amount = amount;
    }

    @Nonnull
    public static TakeItemEntityPacket create(int entityId, int playerId, int amount) {
        return Mapping.get().packetManager().outgoing().takeItemEntityPacket(entityId, playerId, amount);
    }
}
