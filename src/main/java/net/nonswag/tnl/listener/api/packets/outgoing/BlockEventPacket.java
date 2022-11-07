package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Material;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BlockEventPacket extends PacketBuilder {
    private BlockPosition position;
    private Material blockType;
    private int type, data;

    public static BlockEventPacket create(BlockPosition position, Material blockType, int type, int data) {
        return Mapping.get().packetManager().outgoing().blockEventPacket(position, blockType, type, data);
    }
}
