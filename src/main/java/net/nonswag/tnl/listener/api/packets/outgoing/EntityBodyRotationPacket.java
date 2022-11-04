package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EntityBodyRotationPacket extends PacketBuilder {

    private int entityId;
    private float rotation;

    public static EntityBodyRotationPacket create(int entityId, float rotation) {
        return Mapping.get().packetManager().outgoing().entityBodyRotationPacket(entityId, rotation);
    }

    public static EntityBodyRotationPacket create(Entity entity, float rotation) {
        return create(entity.getEntityId(), rotation);
    }

    public static EntityBodyRotationPacket create(Entity entity) {
        return create(entity.getEntityId(), entity.getLocation().getYaw());
    }
}
