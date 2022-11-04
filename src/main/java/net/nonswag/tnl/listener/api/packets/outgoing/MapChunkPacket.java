package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Chunk;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MapChunkPacket extends PacketBuilder {

    private Chunk chunk;
    private int section;

    public static MapChunkPacket create(Chunk chunk, int section) {
        return Mapping.get().packetManager().outgoing().mapChunkPacket(chunk, section);
    }

    public static MapChunkPacket create(Chunk chunk) {
        return create(chunk, 1);
    }
}
