package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.util.Vector;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ExplodePacket extends PacketBuilder {
    private Position position;
    private float radius;
    private List<BlockPosition> affectedBlocks;
    private Vector knockback;

    public static ExplodePacket create(Position position, float radius, List<BlockPosition> affectedBlocks, Vector knockback) {
        return Mapping.get().packetManager().outgoing().explodePacket(position, radius, affectedBlocks, knockback);
    }
}
