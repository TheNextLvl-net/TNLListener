package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SetCommandBlockPacket implements IncomingPacket {
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private String command;
    @Nonnull
    private Mode mode;
    private boolean trackOutput;
    private boolean conditional;
    private boolean alwaysActive;

    public SetCommandBlockPacket(@Nonnull BlockPosition position, @Nonnull String command, @Nonnull Mode mode,
                                 boolean trackOutput, boolean conditional, boolean alwaysActive) {
        this.position = position;
        this.command = command;
        this.mode = mode;
        this.trackOutput = trackOutput;
        this.conditional = conditional;
        this.alwaysActive = alwaysActive;
    }

    public enum Mode {
        SEQUENCE, AUTO, REDSTONE
    }
}
