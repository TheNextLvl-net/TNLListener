package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

@Getter
public abstract class LivingEntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private LivingEntity entity;

    protected LivingEntitySpawnPacket(@Nonnull LivingEntity entity) {
        this.entity = entity;
    }

    @Nonnull
    public LivingEntitySpawnPacket setEntity(@Nonnull LivingEntity entity) {
        this.entity = entity;
        return this;
    }

    @Nonnull
    public static LivingEntitySpawnPacket create(@Nonnull LivingEntity entity) {
        return Mapping.get().packets().livingEntitySpawnPacket(entity);
    }
}
