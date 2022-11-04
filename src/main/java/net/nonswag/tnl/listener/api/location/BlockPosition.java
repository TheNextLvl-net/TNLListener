package net.nonswag.tnl.listener.api.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(chain = true)
public class BlockPosition {

    private int x, y, z;

    public BlockPosition add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public BlockPosition remove(int x, int y, int z) {
        return add(-x, -y, -z);
    }
}
