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
public abstract class BlockUpdatePacket extends PacketBuilder {
    private BlockPosition position;
    private int blockState;

    public static BlockUpdatePacket create(BlockPosition position, int blockState) {
        return Mapping.get().packetManager().outgoing().blockUpdatePacket(position, blockState);
    }
}
