package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.Vector;

@Getter
@Setter
public abstract class UseItemOnPacket extends PacketBuilder {
    private Hand hand;
    private BlockTargetResult target;
    private int sequence;

    protected UseItemOnPacket(Hand hand, BlockTargetResult target, int sequence) {
        this.hand = hand;
        this.target = target;
        this.sequence = sequence;
    }

    public static UseItemOnPacket create(Hand hand, BlockTargetResult target, int sequence) {
        return Mapping.get().packetManager().incoming().useItemOnPacket(hand, target, sequence);
    }

    @Getter
    @Setter
    public static class BlockTargetResult {
        private Vector location;
        private BlockPosition position;
        private Direction side;
        private boolean missed;
        private boolean insideBlock;

        public BlockTargetResult(boolean missed, Vector location, BlockPosition position, Direction side, boolean insideBlock) {
            this.missed = missed;
            this.location = location;
            this.position = position;
            this.side = side;
            this.insideBlock = insideBlock;
        }
    }
}
