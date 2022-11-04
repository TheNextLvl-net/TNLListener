package net.nonswag.tnl.listener.api.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class Position {

    private double x, y, z;
    private float yaw, pitch;

    public Position(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Position(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Position add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Position remove(double x, double y, double z) {
        return add(-x, -y, -z);
    }
}
