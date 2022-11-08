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
public abstract class SetDefaultSpawnPositionPacket extends PacketBuilder {
    private BlockPosition position;
    private float angle;

    public static SetDefaultSpawnPositionPacket create(BlockPosition position, float angle) {
        return Mapping.get().packetManager().outgoing().setDefaultSpawnPositionPacket(position, angle);
    }
}
