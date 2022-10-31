package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityTagQueryPacket extends PacketBuilder {
    private int transactionId;
    private int entityId;

    protected EntityTagQueryPacket(int transactionId, int entityId) {
        this.transactionId = transactionId;
        this.entityId = entityId;
    }

    @Nonnull
    public static EntityTagQueryPacket create(int transactionId, int entityId) {
        return Mapping.get().packetManager().incoming().entityTagQueryPacket(transactionId, entityId);
    }
}
