package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TeleportEntityPacket extends PacketBuilder {
    private int entityId;
    private Position position;
    private boolean onGround;

    public static TeleportEntityPacket create(int entityId, Position position, boolean onGround) {
        return Mapping.get().packetManager().outgoing().teleportEntityPacket(entityId, position, onGround);
    }

    public static TeleportEntityPacket create(Entity entity, Position position, boolean onGround) {
        return create(entity.getEntityId(), position, onGround);
    }

    public static TeleportEntityPacket create(Entity entity, Position position) {
        return create(entity, position, entity.isOnGround());
    }

    public static TeleportEntityPacket create(Entity entity) {
        return create(entity, new Position(entity.getLocation()));
    }
}
