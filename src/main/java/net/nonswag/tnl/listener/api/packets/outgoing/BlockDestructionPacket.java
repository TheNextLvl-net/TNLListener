package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.block.Block;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BlockDestructionPacket extends PacketBuilder {

    private int id;
    private BlockPosition position;
    private int state;

    public static BlockDestructionPacket create(int id, BlockPosition position, int state) {
        return Mapping.get().packetManager().outgoing().blockDestructionPacket(id, position, state);
    }

    public static BlockDestructionPacket create(Block block, int state) {
        return create(block.hashCode(), new BlockPosition(block.getX(), block.getY(), block.getZ()), state);
    }
}
