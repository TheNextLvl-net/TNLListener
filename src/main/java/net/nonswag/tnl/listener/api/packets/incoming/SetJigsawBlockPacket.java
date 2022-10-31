package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetJigsawBlockPacket extends PacketBuilder {
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private NamespacedKey name;
    @Nonnull
    private NamespacedKey target;
    @Nonnull
    private NamespacedKey pool;
    @Nonnull
    private String finalState;
    @Nonnull
    private JointType joint;

    protected SetJigsawBlockPacket(@Nonnull BlockPosition position, @Nonnull NamespacedKey name,
                                   @Nonnull NamespacedKey target, @Nonnull NamespacedKey pool,
                                   @Nonnull String finalState, @Nonnull JointType joint) {
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

    @Nonnull
    public static SetJigsawBlockPacket create(@Nonnull BlockPosition position, @Nonnull NamespacedKey name,
                                              @Nonnull NamespacedKey target, @Nonnull NamespacedKey pool,
                                              @Nonnull String finalState, @Nonnull JointType joint) {
        return Mapping.get().packetManager().incoming().setJigsawBlockPacket(position, name, target, pool, finalState, joint);
    }
}
