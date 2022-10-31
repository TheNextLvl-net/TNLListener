package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Rotation;
import org.bukkit.block.structure.Mirror;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetStructureBlockPacket extends PacketBuilder {
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private Type type;
    @Nonnull
    private Mode mode;
    @Nonnull
    private String name;
    @Nonnull
    private BlockPosition offset;
    @Nonnull
    private Vector size;
    @Nonnull
    private Mirror mirror;
    @Nonnull
    private Rotation rotation;
    @Nonnull
    private String metadata;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;
    private float integrity;
    private long seed;

    protected SetStructureBlockPacket(@Nonnull BlockPosition position, @Nonnull Type type, @Nonnull Mode mode,
                                      @Nonnull String name, @Nonnull BlockPosition offset, @Nonnull Vector size,
                                      @Nonnull Mirror mirror, @Nonnull Rotation rotation, @Nonnull String metadata,
                                      boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity,
                                      long seed) {
        this.position = position;
        this.type = type;
        this.mode = mode;
        this.name = name;
        this.offset = offset;
        this.size = size;
        this.mirror = mirror;
        this.rotation = rotation;
        this.metadata = metadata;
        this.ignoreEntities = ignoreEntities;
        this.showAir = showAir;
        this.showBoundingBox = showBoundingBox;
        this.integrity = integrity;
        this.seed = seed;
    }

    public enum Mode {
        SAVE, LOAD, CORNER, DATA
    }

    public enum Type {
        UPDATE_DATA, SAVE_AREA, LOAD_AREA, SCAN_AREA
    }

    @Nonnull
    public static SetStructureBlockPacket create(@Nonnull BlockPosition position, @Nonnull Type type, @Nonnull Mode mode,
                                                 @Nonnull String name, @Nonnull BlockPosition offset, @Nonnull Vector size,
                                                 @Nonnull Mirror mirror, @Nonnull Rotation rotation, @Nonnull String metadata,
                                                 boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity,
                                                 long seed) {
        return Mapping.get().packetManager().incoming().setStructureBlockPacket(position, type, mode, name, offset, size, mirror, rotation, metadata, ignoreEntities, showAir, showBoundingBox, integrity, seed);
    }
}
