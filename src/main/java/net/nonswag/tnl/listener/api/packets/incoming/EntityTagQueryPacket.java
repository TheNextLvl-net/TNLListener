package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityTagQueryPacket implements IncomingPacket {
    private int transactionId;
    private int entityId;

    public EntityTagQueryPacket(int transactionId, int entityId) {
        this.transactionId = transactionId;
        this.entityId = entityId;
    }
}
