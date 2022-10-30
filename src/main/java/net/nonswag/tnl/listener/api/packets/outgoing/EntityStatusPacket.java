package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityStatusPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Status status;

    protected EntityStatusPacket(int entityId, @Nonnull Status status) {
        this.entityId = entityId;
        this.status = status;
    }

    @Nonnull
    public EntityStatusPacket setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityStatusPacket setStatus(@Nonnull Status status) {
        this.status = status;
        return this;
    }

    @Nonnull
    public static EntityStatusPacket create(int entityId, @Nonnull Status status) {
        return Mapping.get().packets().entityStatusPacket(entityId, status);
    }

    @Nonnull
    public static EntityStatusPacket create(@Nonnull Entity entity, @Nonnull Status status) {
        return create(entity.getEntityId(), status);
    }

    public enum Status {
        BURNING((byte) 0x01),
        CROUCHING((byte) 0x02),
        SPRINTING((byte) 0x08),
        STOP_EATING((byte) 0x09),
        SWIMMING((byte) 0x10),
        INVISIBLE((byte) 0x20),
        GLOWING((byte) 0x40),
        ELYTRA_FLY((byte) 0x80),
        REDUCED_DEBUG_INFO_ON((byte) 22),
        REDUCED_DEBUG_INFO_OFF((byte) 23),
        PERMISSION_LEVEL_4((byte) 28),
        PERMISSION_LEVEL_3((byte) 27),
        PERMISSION_LEVEL_2((byte) 26),
        PERMISSION_LEVEL_1((byte) 25),
        PERMISSION_LEVEL_0((byte) 24);

        private final byte id;

        Status(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }

        @Nonnull
        public static Status reducedDebugInformation(boolean reduced) {
            return reduced ? REDUCED_DEBUG_INFO_ON : REDUCED_DEBUG_INFO_OFF;
        }
    }
}
