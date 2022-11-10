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
public abstract class SetCameraPacket extends PacketBuilder {
    private int targetId;

    public static SetCameraPacket create(int targetId) {
        return Mapping.get().packetManager().outgoing().setCameraPacket(targetId);
    }

    public static SetCameraPacket create(Entity target) {
        return create(target.getEntityId());
    }
}
