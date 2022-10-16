package net.nonswag.tnl.listener.api.mods.labymod.data;

import org.bukkit.Location;

import javax.annotation.Nonnull;

public record Point(double x, double y, double z, float yaw, float pitch, double tilt) {

    @Nonnull
    public static Point of(@Nonnull Location location) {
        return of(location, 0);
    }

    @Nonnull
    public static Point of(@Nonnull Location location, double tilt) {
        return new Point(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), tilt);
    }
}
