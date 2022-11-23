package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerActionPacket extends PacketBuilder {
    private Action action;
    private BlockPosition position;
    private Direction direction;
    private int sequence;

    public enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_ITEM_WITH_OFFHAND;

        public boolean isInteraction() {
            return equals(ABORT_DESTROY_BLOCK) || equals(STOP_DESTROY_BLOCK) || equals(START_DESTROY_BLOCK);
        }

        public boolean isItemAction() {
            return equals(DROP_ITEM) || equals(DROP_ALL_ITEMS) || equals(RELEASE_USE_ITEM) || equals(SWAP_ITEM_WITH_OFFHAND);
        }
    }

    public static PlayerActionPacket create(Action action, BlockPosition position, Direction direction, int sequence) {
        return Mapping.get().packetManager().incoming().playerActionPacket(action, position, direction, sequence);
    }
}
