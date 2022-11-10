package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EntityEventPacket extends PacketBuilder {
    private int entityId;
    private byte eventId;

    public static EntityEventPacket create(int entityId, byte eventId) {
        return Mapping.get().packetManager().outgoing().entityEventPacket(entityId, eventId);
    }
}
