package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Rotation;
import org.bukkit.block.structure.Mirror;
import org.bukkit.util.Vector;

@Getter
@Setter
public abstract class SetStructureBlockPacket extends PacketBuilder {
    private BlockPosition position;
    private Type type;
    private Mode mode;
    private String name;
    private BlockPosition offset;
    private Vector size;
    private Mirror mirror;
    private Rotation rotation;
    private String metadata;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;
    private float integrity;
    private long seed;

    protected SetStructureBlockPacket(BlockPosition position, Type type, Mode mode,
                                      String name, BlockPosition offset, Vector size,
                                      Mirror mirror, Rotation rotation, String metadata,
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

    public static SetStructureBlockPacket create(BlockPosition position, Type type, Mode mode,
                                                 String name, BlockPosition offset, Vector size,
                                                 Mirror mirror, Rotation rotation, String metadata,
                                                 boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity,
                                                 long seed) {
        return Mapping.get().packetManager().incoming().setStructureBlockPacket(position, type, mode, name, offset, size, mirror, rotation, metadata, ignoreEntities, showAir, showBoundingBox, integrity, seed);
    }
}
