package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityVelocityPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Vector vector;

    protected EntityVelocityPacket(int entityId, @Nonnull Vector vector) {
        this.entityId = entityId;
        this.vector = vector;
    }

    @Nonnull
    public EntityVelocityPacket setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityVelocityPacket setVector(@Nonnull Vector vector) {
        this.vector = vector;
        return this;
    }

    @Nonnull
    public static EntityVelocityPacket create(int entityId, @Nonnull Vector vector) {
        return Mapping.get().packets().entityVelocityPacket(entityId, vector);
    }

    @Nonnull
    public static EntityVelocityPacket create(@Nonnull Entity entity, @Nonnull Vector vector) {
        return create(entity.getEntityId(), vector);
    }
}
