package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class RotateHeadPacket extends PacketBuilder {
    private int entityId;
    private byte yaw;

    protected RotateHeadPacket(int entityId, byte yaw) {
        this.entityId = entityId;
        this.yaw = yaw;
    }

    @Nonnull
    public static RotateHeadPacket create(int entityId, byte yaw) {
        return Mapping.get().packetManager().outgoing().rotateHeadPacket(entityId, yaw);
    }
}
