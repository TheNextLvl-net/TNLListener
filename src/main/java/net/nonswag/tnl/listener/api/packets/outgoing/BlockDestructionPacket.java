package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class BlockDestructionPacket extends PacketBuilder {

    @Nonnull
    private BlockPosition position;
    private int id, state;

    protected BlockDestructionPacket(int id, @Nonnull BlockPosition position, int state) {
        this.id = id;
        this.position = position;
        this.state = state;
    }

    @Nonnull
    public static BlockDestructionPacket create(int id, @Nonnull BlockPosition position, int state) {
        return Mapping.get().packetManager().outgoing().blockDestructionPacket(id, position, state);
    }

    @Nonnull
    public static BlockDestructionPacket create(@Nonnull Block block, int state) {
        return create(block.hashCode(), new BlockPosition(block.getX(), block.getY(), block.getZ()), state);
    }
}
