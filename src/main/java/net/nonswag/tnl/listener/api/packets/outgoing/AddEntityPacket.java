package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AddEntityPacket extends PacketBuilder {
    private int entityId;
    private UUID uniqueId;
    private Position position;
    private EntityType entityType;
    private int entityData;
    private Vector velocity;
    private double headYaw;

    public static AddEntityPacket create(int entityId, UUID uniqueId, Position position, EntityType entityType, int entityData, Vector velocity, double headYaw) {
        return Mapping.get().packetManager().outgoing().addEntityPacket(entityId, uniqueId, position, entityType, entityData, velocity, headYaw);
    }

    public static AddEntityPacket create(Entity entity) {
        return create(entity.getEntityId(), entity.getUniqueId(), new Position(entity.getLocation()), entity.getType(), 0, entity.getVelocity(), 0);
    }
}
