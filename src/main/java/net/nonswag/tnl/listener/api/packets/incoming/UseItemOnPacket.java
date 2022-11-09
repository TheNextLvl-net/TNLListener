package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.Vector;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UseItemOnPacket extends PacketBuilder {
    private Hand hand;
    private BlockTargetResult target;
    private int sequence;

    public static UseItemOnPacket create(Hand hand, BlockTargetResult target, int sequence) {
        return Mapping.get().packetManager().incoming().useItemOnPacket(hand, target, sequence);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class BlockTargetResult {
        private boolean missed;
        private Vector location;
        private BlockPosition position;
        private Direction side;
        private boolean insideBlock;
    }
}
