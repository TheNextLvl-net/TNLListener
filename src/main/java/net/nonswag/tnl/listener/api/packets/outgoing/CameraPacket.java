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
public abstract class CameraPacket extends PacketBuilder {

    private int targetId;

    public static CameraPacket create(int targetId) {
        return Mapping.get().packetManager().outgoing().cameraPacket(targetId);
    }

    public static CameraPacket create(Entity target) {
        return create(target.getEntityId());
    }
}
