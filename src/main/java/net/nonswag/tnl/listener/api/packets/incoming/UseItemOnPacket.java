package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class UseItemOnPacket extends PacketBuilder {
    @Nonnull
    private Hand hand;
    @Nonnull
    private BlockTargetResult target;
    private int sequence;

    protected UseItemOnPacket(@Nonnull Hand hand, @Nonnull BlockTargetResult target, int sequence) {
        this.hand = hand;
        this.target = target;
        this.sequence = sequence;
    }

    @Nonnull
    public static UseItemOnPacket create(@Nonnull Hand hand, @Nonnull BlockTargetResult target, int sequence) {
        return Mapping.get().packetManager().incoming().useItemOnPacket(hand, target, sequence);
    }

    @Getter
    @Setter
    public static class BlockTargetResult {
        @Nonnull
        private Vector location;
        @Nonnull
        private BlockPosition position;
        @Nonnull
        private Direction side;
        private boolean missed;
        private boolean insideBlock;

        public BlockTargetResult(boolean missed, @Nonnull Vector location, @Nonnull BlockPosition position, @Nonnull Direction side, boolean insideBlock) {
            this.missed = missed;
            this.location = location;
            this.position = position;
            this.side = side;
            this.insideBlock = insideBlock;
        }
    }
}
