package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EntityStatusPacket extends PacketBuilder {

    private int entityId;
    private Status status;

    public static EntityStatusPacket create(int entityId, Status status) {
        return Mapping.get().packetManager().outgoing().entityStatusPacket(entityId, status);
    }

    public static EntityStatusPacket create(Entity entity, Status status) {
        return create(entity.getEntityId(), status);
    }

    @Getter
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

        public static Status reducedDebugInformation(boolean reduced) {
            return reduced ? REDUCED_DEBUG_INFO_ON : REDUCED_DEBUG_INFO_OFF;
        }
    }
}
