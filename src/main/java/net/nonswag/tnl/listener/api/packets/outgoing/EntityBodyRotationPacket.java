package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityBodyRotationPacket extends PacketBuilder {

    private int entityId;
    private float rotation;

    protected EntityBodyRotationPacket(int entityId, float rotation) {
        this.entityId = entityId;
        this.rotation = rotation;
    }

    @Nonnull
    public static EntityBodyRotationPacket create(int entityId, float rotation) {
        return Mapping.get().packetManager().outgoing().entityBodyRotationPacket(entityId, rotation);
    }

    @Nonnull
    public static EntityBodyRotationPacket create(@Nonnull Entity entity, float rotation) {
        return create(entity.getEntityId(), rotation);
    }

    @Nonnull
    public static EntityBodyRotationPacket create(@Nonnull Entity entity) {
        return create(entity.getEntityId(), entity.getLocation().getYaw());
    }
}
