package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class LivingEntitySpawnPacket extends PacketBuilder {

    @Nonnull
    private LivingEntity entity;

    protected LivingEntitySpawnPacket(@Nonnull LivingEntity entity) {
        this.entity = entity;
    }

    @Nonnull
    public static LivingEntitySpawnPacket create(@Nonnull LivingEntity entity) {
        return Mapping.get().packetManager().outgoing().livingEntitySpawnPacket(entity);
    }
}
