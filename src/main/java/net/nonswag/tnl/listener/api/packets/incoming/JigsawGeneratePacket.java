package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class JigsawGeneratePacket extends PacketBuilder {
    private BlockPosition position;
    private int levels;
    private boolean keepJigsaws;

    protected JigsawGeneratePacket(BlockPosition position, int levels, boolean keepJigsaws) {
        this.position = position;
        this.levels = levels;
        this.keepJigsaws = keepJigsaws;
    }

    public static JigsawGeneratePacket create(BlockPosition position, int levels, boolean keepJigsaws) {
        return Mapping.get().packetManager().incoming().jigsawGeneratePacket(position, levels, keepJigsaws);
    }
}
