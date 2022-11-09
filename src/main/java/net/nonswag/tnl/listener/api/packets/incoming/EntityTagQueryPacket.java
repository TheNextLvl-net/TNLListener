package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EntityTagQueryPacket extends PacketBuilder {
    private int transactionId, entityId;

    public static EntityTagQueryPacket create(int transactionId, int entityId) {
        return Mapping.get().packetManager().incoming().entityTagQueryPacket(transactionId, entityId);
    }
}
