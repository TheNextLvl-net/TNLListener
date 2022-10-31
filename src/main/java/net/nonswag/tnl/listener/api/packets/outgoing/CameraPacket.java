package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CameraPacket extends PacketBuilder {

    private int targetId;

    protected CameraPacket(int targetId) {
        this.targetId = targetId;
    }

    @Nonnull
    public static CameraPacket create(int targetId) {
        return Mapping.get().packetManager().outgoing().cameraPacket(targetId);
    }

    @Nonnull
    public static CameraPacket create(@Nonnull Entity target) {
        return create(target.getEntityId());
    }
}
