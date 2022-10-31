package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class EntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private Entity entity;

    protected EntitySpawnPacket(@Nonnull Entity entity) {
        this.entity = entity;
    }

    @Nonnull
    public static EntitySpawnPacket create(@Nonnull Entity entity) {
        return Mapping.get().packetManager().outgoing().entitySpawnPacket(entity);
    }
}
