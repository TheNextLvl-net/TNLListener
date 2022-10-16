package net.nonswag.tnl.listener.api.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockLocation extends Location {

    public BlockLocation(@Nonnull Location location) {
        this(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public BlockLocation(@Nonnull Block block) {
        this(block.getLocation());
    }

    public BlockLocation(@Nonnull String world, int x, int y, int z) {
        this(Bukkit.getWorld(world), x, y, z);
    }

    public BlockLocation(@Nullable World world, int x, int y, int z) {
        super(world, x, y, z);
    }

    public BlockLocation(@Nullable World world, double x, double y, double z) {
        this(world, (int) x, (int) y, (int) z);
    }

    public BlockLocation(@Nullable World world, double x, double y, double z, float yaw, float pitch) {
        this(world, x, y, z);
    }

    @Override
    public final void setX(double x) {
        super.setX((int) x);
    }

    @Override
    public final void setY(double y) {
        super.setY((int) y);
    }

    @Override
    public final void setZ(double z) {
        super.setZ((int) z);
    }

    @Override
    public final void setPitch(float pitch) {
        super.setPitch(0);
    }

    @Override
    public final void setYaw(float yaw) {
        super.setYaw(0);
    }

    @Nonnull
    @Override
    public final BlockLocation add(double x, double y, double z) {
        super.add((int) x, (int) y, (int) z);
        return this;
    }

    @Nonnull
    @Override
    public final BlockLocation subtract(double x, double y, double z) {
        super.subtract((int) x, (int) y, (int) z);
        return this;
    }

    @Override
    public final double getX() {
        return getBlockX();
    }

    @Override
    public final double getY() {
        return getBlockY();
    }

    @Override
    public final double getZ() {
        return getBlockZ();
    }

    @Deprecated
    @Override
    public final float getYaw() {
        return 0;
    }

    @Deprecated
    @Override
    public final float getPitch() {
        return 0;
    }

    @Nonnull
    @Override
    public BlockLocation clone() {
        return new BlockLocation(getWorld(), getX(), getY(), getZ());
    }

    @Nonnull
    public Location toLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Location location)) return false;
        if (location == this) return true;
        if (location.getWorld() != getWorld()) return false;
        if (location.getBlockX() != getBlockX()) return false;
        if (location.getBlockY() != getBlockY()) return false;
        return location.getBlockZ() == getBlockZ();
    }

    @Override
    public int hashCode() {
        int hash = 57 + (getWorld() == null ? 0 : getWorld().hashCode());
        hash = 19 * hash + (int) (Double.doubleToLongBits(getBlockX()) ^ Double.doubleToLongBits(getBlockX()) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(getBlockY()) ^ Double.doubleToLongBits(getBlockY()) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(getBlockZ()) ^ Double.doubleToLongBits(getBlockX()) >>> 32);
        return hash;
    }

    @Nonnull
    @Override
    public String toString() {
        return "BlockLocation{world=" + getWorld() + ",x=" + getX() + ",y=" + getY() + ",z=" + getZ() + '}';
    }
}
