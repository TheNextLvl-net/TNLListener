package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityDestroyPacket extends PacketBuilder {

    private int[] destroyIds;

    protected EntityDestroyPacket(int... destroyIds) {
        this.destroyIds = destroyIds;
    }

    @Nonnull
    public EntityDestroyPacket setDestroyIds(int[] destroyIds) {
        this.destroyIds = destroyIds;
        return this;
    }

    @Nonnull
    public static EntityDestroyPacket create(int... destroyIds) {
        return Mapping.get().packets().entityDestroyPacket(destroyIds);
    }

    @Nonnull
    public static EntityDestroyPacket create(@Nonnull Entity... entities) {
        int[] destroyIds = new int[entities.length];
        for (int i = 0; i < entities.length; i++) destroyIds[i] = entities[i].getEntityId();
        return create(destroyIds);
    }
}
