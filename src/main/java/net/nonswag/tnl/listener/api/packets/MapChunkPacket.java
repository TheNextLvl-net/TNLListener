package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Chunk;

import javax.annotation.Nonnull;

@Getter
public abstract class MapChunkPacket extends PacketBuilder {

    @Nonnull
    private Chunk chunk;
    private int section;

    protected MapChunkPacket(@Nonnull Chunk chunk, int section) {
        this.chunk = chunk;
        this.section = section;
    }

    @Nonnull
    public MapChunkPacket setChunk(@Nonnull Chunk chunk) {
        this.chunk = chunk;
        return this;
    }

    @Nonnull
    public MapChunkPacket setSection(int section) {
        this.section = section;
        return this;
    }

    @Nonnull
    public static MapChunkPacket create(@Nonnull Chunk chunk, int section) {
        return Mapping.get().packets().mapChunkPacket(chunk, section);
    }

    @Nonnull
    public static MapChunkPacket create(@Nonnull Chunk chunk) {
        return create(chunk, 1);
    }
}
