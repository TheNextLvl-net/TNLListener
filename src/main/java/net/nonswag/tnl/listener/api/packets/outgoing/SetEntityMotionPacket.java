package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetEntityMotionPacket extends PacketBuilder {
    private int entityId;
    private Vector velocity;

    public static SetEntityMotionPacket create(int entityId, Vector velocity) {
        return Mapping.get().packetManager().outgoing().setEntityMotionPacket(entityId, velocity);
    }

    public static SetEntityMotionPacket create(Entity entity, Vector velocity) {
        return create(entity.getEntityId(), velocity);
    }
}
