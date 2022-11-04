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
public abstract class EntityMetadataPacket<M> extends PacketBuilder {

    private int entityId;
    private M metadata;
    private boolean updateAll;

    public static <M> EntityMetadataPacket<M> create(int entityId, M dataWatcher, boolean updateAll) {
        return Mapping.get().packetManager().outgoing().entityMetadataPacket(entityId, dataWatcher, updateAll);
    }

    public static <M> EntityMetadataPacket<M> create(int entityId, M dataWatcher) {
        return create(entityId, dataWatcher, true);
    }

    public static <M> EntityMetadataPacket<M> create(Entity entity, M dataWatcher, boolean updateAll) {
        return create(entity.getEntityId(), dataWatcher, updateAll);
    }

    public static <M> EntityMetadataPacket<M> create(Entity entity, M dataWatcher) {
        return create(entity.getEntityId(), dataWatcher);
    }

    public static <M> EntityMetadataPacket<M> create(Entity entity) {
        return create(entity, true);
    }

    public static <M> EntityMetadataPacket<M> create(Entity entity, boolean updateAll) {
        return Mapping.get().packetManager().outgoing().entityMetadataPacket(entity, updateAll);
    }
}
