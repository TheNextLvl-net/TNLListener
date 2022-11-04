package net.nonswag.tnl.listener.api.mods.labymod.data;

import org.bukkit.Location;

public record Point(double x, double y, double z, float yaw, float pitch, double tilt) {

    public static Point of(Location location) {
        return of(location, 0);
    }

    public static Point of(Location location, double tilt) {
        return new Point(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), tilt);
    }
}
