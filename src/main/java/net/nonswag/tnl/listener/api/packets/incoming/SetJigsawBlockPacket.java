package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetJigsawBlockPacket extends PacketBuilder {
    private BlockPosition position;
    private NamespacedKey name, target, pool;
    private String finalState;
    private JointType joint;

    public enum JointType {
        ROLLABLE, ALIGNED
    }

    public static SetJigsawBlockPacket create(BlockPosition position, NamespacedKey name,
                                              NamespacedKey target, NamespacedKey pool,
                                              String finalState, JointType joint) {
        return Mapping.get().packetManager().incoming().setJigsawBlockPacket(position, name, target, pool, finalState, joint);
    }
}
