package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityTeleportPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Position position;

    protected EntityTeleportPacket(int entityId, @Nonnull Position position) {
        this.entityId = entityId;
        this.position = position;
    }

    @Nonnull
    public EntityTeleportPacket setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityTeleportPacket setPosition(@Nonnull Position position) {
        this.position = position;
        return this;
    }

    @Nonnull
    public static EntityTeleportPacket create(int entityId, @Nonnull Position position) {
        return Mapping.get().packets().entityTeleportPacket(entityId, position);
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
