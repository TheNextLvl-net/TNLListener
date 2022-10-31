package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Chunk;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class MapChunkPacket extends PacketBuilder {

    @Nonnull
    private Chunk chunk;
    private int section;

    protected MapChunkPacket(@Nonnull Chunk chunk, int section) {
        this.chunk = chunk;
        this.section = section;
    }

    @Nonnull
    public static MapChunkPacket create(@Nonnull Chunk chunk, int section) {
        return Mapping.get().packetManager().outgoing().mapChunkPacket(chunk, section);
    }

    @Nonnull
    public static MapChunkPacket create(@Nonnull Chunk chunk) {
        return create(chunk, 1);
    }
}
