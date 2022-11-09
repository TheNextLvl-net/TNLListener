package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class JigsawGeneratePacket extends PacketBuilder {
    private BlockPosition position;
    private int levels;
    private boolean keepJigsaws;

    public static JigsawGeneratePacket create(BlockPosition position, int levels, boolean keepJigsaws) {
        return Mapping.get().packetManager().incoming().jigsawGeneratePacket(position, levels, keepJigsaws);
    }
}
