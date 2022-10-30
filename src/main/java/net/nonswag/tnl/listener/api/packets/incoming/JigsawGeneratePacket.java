package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;

import javax.annotation.Nonnull;

@Getter
@Setter
public class JigsawGeneratePacket implements IncomingPacket {
    @Nonnull
    private BlockPosition position;
    private int levels;
    private boolean keepJigsaws;

    public JigsawGeneratePacket(@Nonnull BlockPosition position, int levels, boolean keepJigsaws) {
        this.position = position;
        this.levels = levels;
        this.keepJigsaws = keepJigsaws;
    }
}
