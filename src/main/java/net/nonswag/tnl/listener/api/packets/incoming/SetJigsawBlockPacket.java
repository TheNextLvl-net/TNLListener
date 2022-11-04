package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
public abstract class SetJigsawBlockPacket extends PacketBuilder {
    private BlockPosition position;
    private NamespacedKey name;
    private NamespacedKey target;
    private NamespacedKey pool;
    private String finalState;
    private JointType joint;

    protected SetJigsawBlockPacket(BlockPosition position, NamespacedKey name,
                                   NamespacedKey target, NamespacedKey pool,
                                   String finalState, JointType joint) {
        this.position = position;
        this.name = name;
        this.target = target;
        this.pool = pool;
        this.finalState = finalState;
        this.joint = joint;
    }

    public enum JointType {
        ROLLABLE, ALIGNED
    }

    public static SetJigsawBlockPacket create(BlockPosition position, NamespacedKey name,
                                              NamespacedKey target, NamespacedKey pool,
                                              String finalState, JointType joint) {
        return Mapping.get().packetManager().incoming().setJigsawBlockPacket(position, name, target, pool, finalState, joint);
    }
}
