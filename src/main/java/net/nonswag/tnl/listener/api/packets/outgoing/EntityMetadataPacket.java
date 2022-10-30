package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityMetadataPacket<W> extends PacketBuilder {

    private int entityId;
    @Nonnull
    private W dataWatcher;
    private boolean updateAll;

    protected EntityMetadataPacket(int entityId, @Nonnull W dataWatcher, boolean updateAll) {
        this.entityId = entityId;
        this.dataWatcher = dataWatcher;
        this.updateAll = updateAll;
    }

    @Nonnull
    public EntityMetadataPacket<W> setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityMetadataPacket<W> setDataWatcher(@Nonnull W dataWatcher) {
        this.dataWatcher = dataWatcher;
        return this;
    }

    @Nonnull
    public EntityMetadataPacket<W> setUpdateAll(boolean updateAll) {
        this.updateAll = updateAll;
        return this;
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(int entityId, @Nonnull W dataWatcher, boolean updateAll) {
        return Mapping.get().packets().entityMetadataPacket(entityId, dataWatcher, updateAll);
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(int entityId, @Nonnull W dataWatcher) {
        return create(entityId, dataWatcher, true);
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(@Nonnull Entity entity, @Nonnull W dataWatcher, boolean updateAll) {
        return create(entity.getEntityId(), dataWatcher, updateAll);
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(@Nonnull Entity entity, @Nonnull W dataWatcher) {
        return create(entity.getEntityId(), dataWatcher);
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(@Nonnull Entity entity) {
        return create(entity, true);
    }

    @Nonnull
    public static <W> EntityMetadataPacket<W> create(@Nonnull Entity entity, boolean updateAll) {
        return Mapping.get().packets().entityMetadataPacket(entity, updateAll);
    }
}
