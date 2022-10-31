package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityTeleportPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Position position;

    protected EntityTeleportPacket(int entityId, @Nonnull Position position) {
        this.entityId = entityId;
        this.position = position;
    }

    @Nonnull
    public static EntityTeleportPacket create(int entityId, @Nonnull Position position) {
        return Mapping.get().packetManager().outgoing().entityTeleportPacket(entityId, position);
    }

    @Nonnull
    public static EntityTeleportPacket create(int entityId, @Nonnull Location location) {
        return create(entityId, new Position(location));
    }

    @Nonnull
    public static EntityTeleportPacket create(@Nonnull Entity entity, @Nonnull Position position) {
        return create(entity.getEntityId(), position);
    }

    @Nonnull
    public static EntityTeleportPacket create(@Nonnull Entity entity, @Nonnull Location location) {
        return create(entity.getEntityId(), location);
    }

    @Nonnull
    public static EntityTeleportPacket create(@Nonnull Entity entity) {
        return create(entity, entity.getLocation());
    }
}
