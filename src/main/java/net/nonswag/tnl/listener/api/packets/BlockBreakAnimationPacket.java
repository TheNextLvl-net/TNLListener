package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

@Getter
public abstract class BlockBreakAnimationPacket extends PacketBuilder {

    @Nonnull
    private BlockLocation location;
    private int state;

    protected BlockBreakAnimationPacket(@Nonnull BlockLocation location, int state) {
        this.location = location;
        this.state = state;
    }

    @Nonnull
    public BlockBreakAnimationPacket setLocation(@Nonnull BlockLocation location) {
        this.location = location;
        return this;
    }

    @Nonnull
    public BlockBreakAnimationPacket setState(int state) {
        this.state = state;
        return this;
    }

    @Nonnull
    public static BlockBreakAnimationPacket create(@Nonnull BlockLocation location, int state) {
        return Mapping.get().packets().blockBreakAnimationPacket(location, state);
    }

    @Nonnull
    public static BlockBreakAnimationPacket create(@Nonnull Block block, int state) {
        return create(new BlockLocation(block.getLocation()), state);
    }
}
