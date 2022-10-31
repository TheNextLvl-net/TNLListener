package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class MovePlayerPacket extends PacketBuilder {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean changingPosition;
    private boolean changingLook;

    protected MovePlayerPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changingPosition, boolean changingLook) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.changingPosition = changingPosition;
        this.changingLook = changingLook;
    }

    public static abstract class Position extends MovePlayerPacket {
        protected Position(double x, double y, double z, boolean onGround) {
            super(x, y, z, 0, 0, onGround, true, false);
        }

        @Nonnull
        public static Position create(double x, double y, double z, boolean onGround) {
            return Mapping.get().packetManager().incoming().movePlayerPacket(x, y, z, onGround);
        }
    }

    public static abstract class PositionRotation extends MovePlayerPacket {
        public PositionRotation(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(x, y, z, yaw, pitch, onGround, true, true);
        }

        @Nonnull
        public static PositionRotation create(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            return Mapping.get().packetManager().incoming().movePlayerPacket(x, y, z, yaw, pitch, onGround);
        }
    }

    public static abstract class Rotation extends MovePlayerPacket {
        public Rotation(float yaw, float pitch, boolean onGround) {
            super(0, 0, 0, yaw, pitch, onGround, false, true);
        }

        @Nonnull
        public static Rotation create(float yaw, float pitch, boolean onGround) {
            return Mapping.get().packetManager().incoming().movePlayerPacket(yaw, pitch, onGround);
        }
    }

    public static abstract class Status extends MovePlayerPacket {
        public Status(boolean onGround) {
            super(0, 0, 0, 0, 0, onGround, false, false);
        }

        @Nonnull
        public static Status create(boolean onGround) {
            return Mapping.get().packetManager().incoming().movePlayerPacket(onGround);
        }
    }
}
