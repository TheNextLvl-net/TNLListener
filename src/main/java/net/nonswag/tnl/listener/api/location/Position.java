package net.nonswag.tnl.listener.api.location;

import lombok.Getter;
import org.bukkit.Location;

import javax.annotation.Nonnull;

@Getter
public class Position {

    private double x, y, z;
    private float yaw, pitch;

    public Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Position(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Position(@Nonnull Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Nonnull
    public Position setX(double x) {
        this.x = x;
        return this;
    }

    @Nonnull
    public Position setY(double y) {
        this.y = y;
        return this;
    }

    @Nonnull
    public Position setZ(double z) {
        this.z = z;
        return this;
    }

    @Nonnull
    public Position setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    @Nonnull
    public Position setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    @Nonnull
    public Position add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Nonnull
    public Position remove(double x, double y, double z) {
        return add(-x, -y, -z);
    }
}
