package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlayerActionPacket extends PacketBuilder {
    @Nonnull
    private Action action;
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private Direction direction;
    private int sequence;

    protected PlayerActionPacket(@Nonnull Action action, @Nonnull BlockPosition position, @Nonnull Direction direction, int sequence) {
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

    @Nonnull
    public static PlayerActionPacket create(@Nonnull Action action, @Nonnull BlockPosition position, @Nonnull Direction direction, int sequence) {
        return Mapping.get().packetManager().incoming().playerActionPacket(action, position, direction, sequence);
    }
}
