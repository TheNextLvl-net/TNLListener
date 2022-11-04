package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RotateHeadPacket extends PacketBuilder {
    private int entityId;
    private byte yaw;

    public static RotateHeadPacket create(int entityId, byte yaw) {
        return Mapping.get().packetManager().outgoing().rotateHeadPacket(entityId, yaw);
    }
}
