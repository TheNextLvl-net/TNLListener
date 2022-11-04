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
public abstract class EntityHeadRotationPacket extends PacketBuilder {

    private int entityId;
    private float yaw;

    public static EntityHeadRotationPacket create(int entityId, float yaw) {
        return Mapping.get().packetManager().outgoing().entityHeadRotationPacket(entityId, yaw);
    }

    public static EntityHeadRotationPacket create(Entity entity, float yaw) {
        return create(entity.getEntityId(), yaw);
    }

    public static EntityHeadRotationPacket create(Entity entity) {
        return create(entity.getEntityId(), entity.getLocation().getYaw());
    }
}
