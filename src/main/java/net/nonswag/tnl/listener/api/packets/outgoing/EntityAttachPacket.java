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
public abstract class EntityAttachPacket extends PacketBuilder {

    private int holderId;
    private int leashedId;

    public static EntityAttachPacket create(int holderId, int leashedId) {
        return Mapping.get().packetManager().outgoing().entityAttachPacket(holderId, leashedId);
    }

    public static EntityAttachPacket create(int holderId) {
        return create(holderId, 0);
    }

    public static EntityAttachPacket create(Entity holder, Entity leashed) {
        return create(holder.getEntityId(), leashed.getEntityId());
    }

    public static EntityAttachPacket create(Entity holder) {
        return create(holder.getEntityId());
    }
}
