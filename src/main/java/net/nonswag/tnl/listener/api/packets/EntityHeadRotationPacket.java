package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityHeadRotationPacket extends PacketBuilder {

    private int entityId;
    private float yaw;

    protected EntityHeadRotationPacket(int entityId, float yaw) {
        this.entityId = entityId;
        this.yaw = yaw;
    }

    @Nonnull
    public EntityHeadRotationPacket setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityHeadRotationPacket setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    @Nonnull
    public static EntityHeadRotationPacket create(int entityId, float yaw) {
        return Mapping.get().packets().entityHeadRotationPacket(entityId, yaw);
    }

    @Nonnull
    public static EntityHeadRotationPacket create(@Nonnull Entity entity, float yaw) {
        return create(entity.getEntityId(), yaw);
    }

    @Nonnull
    public static EntityHeadRotationPacket create(@Nonnull Entity entity) {
        return create(entity.getEntityId(), entity.getLocation().getYaw());
    }
}
