package net.nonswag.tnl.listener.api.location;

import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public class BlockPosition {

    private int x, y, z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Nonnull
    public BlockPosition setX(int x) {
        this.x = x;
        return this;
    }

    @Nonnull
    public BlockPosition setY(int y) {
        this.y = y;
        return this;
    }

    @Nonnull
    public BlockPosition setZ(int z) {
        this.z = z;
        return this;
    }

    @Nonnull
    public BlockPosition add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Nonnull
    public BlockPosition remove(int x, int y, int z) {
        return add(-x, -y, -z);
    }
}
