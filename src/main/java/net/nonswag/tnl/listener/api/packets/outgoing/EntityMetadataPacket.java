package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityMetadataPacket<M> extends PacketBuilder {

    private int entityId;
    @Nonnull
    private M metadata;
    private boolean updateAll;

    protected EntityMetadataPacket(int entityId, @Nonnull M metadata, boolean updateAll) {
        this.entityId = entityId;
        this.metadata = metadata;
        this.updateAll = updateAll;
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(int entityId, @Nonnull M dataWatcher, boolean updateAll) {
        return Mapping.get().packetManager().outgoing().entityMetadataPacket(entityId, dataWatcher, updateAll);
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(int entityId, @Nonnull M dataWatcher) {
        return create(entityId, dataWatcher, true);
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(@Nonnull Entity entity, @Nonnull M dataWatcher, boolean updateAll) {
        return create(entity.getEntityId(), dataWatcher, updateAll);
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(@Nonnull Entity entity, @Nonnull M dataWatcher) {
        return create(entity.getEntityId(), dataWatcher);
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(@Nonnull Entity entity) {
        return create(entity, true);
    }

    @Nonnull
    public static <M> EntityMetadataPacket<M> create(@Nonnull Entity entity, boolean updateAll) {
        return Mapping.get().packetManager().outgoing().entityMetadataPacket(entity, updateAll);
    }
}
