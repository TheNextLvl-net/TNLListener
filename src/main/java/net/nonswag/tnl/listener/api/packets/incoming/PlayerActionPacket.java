package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;

import javax.annotation.Nonnull;

@Getter
@Setter
public class PlayerActionPacket implements IncomingPacket {
    @Nonnull
    private Action action;
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private Direction direction;
    private int sequence;

    public PlayerActionPacket(@Nonnull Action action, @Nonnull BlockPosition position, @Nonnull Direction direction, int sequence) {
        this.action = action;
        this.position = position;
        this.direction = direction;
        this.sequence = sequence;
    }

    public enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_ITEM_WITH_OFFHAND
    }

    public enum Direction {
        UP, DOWN, NORTH, SOUTH, EAST, WEST
    }
}
