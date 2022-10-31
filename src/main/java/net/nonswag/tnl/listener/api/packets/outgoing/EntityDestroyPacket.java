package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntityDestroyPacket extends PacketBuilder {

    private int[] destroyIds;

    protected EntityDestroyPacket(int... destroyIds) {
        this.destroyIds = destroyIds;
    }

    @Nonnull
    public static EntityDestroyPacket create(int... destroyIds) {
        return Mapping.get().packetManager().outgoing().entityDestroyPacket(destroyIds);
    }

    @Nonnull
    public static EntityDestroyPacket create(@Nonnull Entity... entities) {
        int[] destroyIds = new int[entities.length];
        for (int i = 0; i < entities.length; i++) destroyIds[i] = entities[i].getEntityId();
        return create(destroyIds);
    }
}
