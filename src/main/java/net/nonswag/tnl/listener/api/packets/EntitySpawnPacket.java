package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private Entity entity;

    protected EntitySpawnPacket(@Nonnull Entity entity) {
        this.entity = entity;
    }

    @Nonnull
    public EntitySpawnPacket setEntity(@Nonnull Entity entity) {
        this.entity = entity;
        return this;
    }

    @Nonnull
    public static EntitySpawnPacket create(@Nonnull Entity entity) {
        return Mapping.get().packets().entitySpawnPacket(entity);
    }
}
