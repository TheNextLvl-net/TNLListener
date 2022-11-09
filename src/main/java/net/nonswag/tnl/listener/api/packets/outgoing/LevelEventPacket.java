package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LevelEventPacket extends PacketBuilder {
    private int eventId;
    private BlockPosition position;
    private int data;
    private boolean global;

    public static LevelEventPacket create(int eventId, BlockPosition position, int data, boolean global) {
        return Mapping.get().packetManager().outgoing().levelEventPacket(eventId, position, data, global);
    }
}
