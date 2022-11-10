package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MoveEntityPacket extends PacketBuilder {
    private int entityId;
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround, changingPosition, changingLook;

    public abstract static class Position extends MoveEntityPacket {
        protected Position(int entityId, double x, double y, double z, boolean onGround) {
            super(entityId, x, y, z, 0, 0, onGround, true, false);
        }

        public static Position create(int entityId, double x, double y, double z, boolean onGround) {
            return Mapping.get().packetManager().outgoing().moveEntityPacket(entityId, x, y, z, onGround);
        }
    }

    public abstract static class Rotation extends MoveEntityPacket {
        public Rotation(int entityId, float yaw, float pitch, boolean onGround) {
            super(entityId, 0, 0, 0, yaw, pitch, onGround, false, true);
        }

        public static Rotation create(int entityId, float yaw, float pitch, boolean onGround) {
            return Mapping.get().packetManager().outgoing().moveEntityPacket(entityId, yaw, pitch, onGround);
        }
    }

    public abstract static class PositionRotation extends MoveEntityPacket {
        protected PositionRotation(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(entityId, x, y, z, yaw, pitch, onGround, true, false);
        }

        public static PositionRotation create(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
            return Mapping.get().packetManager().outgoing().moveEntityPacket(entityId, x, y, z, yaw, pitch, onGround);
        }
    }
}
