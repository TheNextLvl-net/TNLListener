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
public abstract class RemoveEntitiesPacket extends PacketBuilder {

    private int[] entityIds;

    public static RemoveEntitiesPacket create(int... entityIds) {
        return Mapping.get().packetManager().outgoing().removeEntitiesPacket(entityIds);
    }

    public static RemoveEntitiesPacket create(Entity... entities) {
        int[] entityIds = new int[entities.length];
        for (int i = 0; i < entities.length; i++) entityIds[i] = entities[i].getEntityId();
        return create(entityIds);
    }
}
