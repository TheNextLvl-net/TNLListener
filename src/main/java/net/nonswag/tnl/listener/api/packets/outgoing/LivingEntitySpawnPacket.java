package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LivingEntitySpawnPacket extends PacketBuilder {
    private LivingEntity entity;

    public static LivingEntitySpawnPacket create(LivingEntity entity) {
        return Mapping.get().packetManager().outgoing().livingEntitySpawnPacket(entity);
    }
}
