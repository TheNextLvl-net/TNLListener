package net.nonswag.tnl.listener.api.border;

import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class VirtualBorder {

    @Nonnull
    private World world;
    @Nonnull
    private Center center;
    private int warningDistance;
    private int warningTime;
    private double damageBuffer;
    private double damageAmount;
    private double size;

    public VirtualBorder(@Nonnull World world, @Nonnull Center center) {
        this.world = world;
        this.center = center;
    }

    public VirtualBorder(@Nonnull World world) {
        this(world, Center.NULL);
    }

    @Nonnull
    public World getWorld() {
        return world;
    }

    public void setWorld(@Nonnull World world) {
        this.world = world;
    }

    @Nonnull
    public Center getCenter() {
        return center;
    }

    public void setCenter(@Nonnull Center center) {
        this.center = center;
    }

    public int getWarningDistance() {
        return warningDistance;
    }

    public void setWarningDistance(int warningDistance) {
        this.warningDistance = warningDistance;
    }

    public int getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.warningTime = warningTime;
    }

    public double getDamageBuffer() {
        return damageBuffer;
    }

    public void setDamageBuffer(double damageBuffer) {
        this.damageBuffer = damageBuffer;
    }

    public double getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(double damageAmount) {
        this.damageAmount = damageAmount;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public record Center(double x, double z) {
        @Nonnull
        public static final Center NULL = new Center(0, 0);
    }

    @Override
    public String toString() {
        return "VirtualBorder{" +
                "world=" + world +
                ", center=" + center +
                ", warningDistance=" + warningDistance +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualBorder that = (VirtualBorder) o;
        return warningDistance == that.warningDistance && Double.compare(that.size, size) == 0 && world.equals(that.world) && center.equals(that.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, center, warningDistance, size);
    }
}
