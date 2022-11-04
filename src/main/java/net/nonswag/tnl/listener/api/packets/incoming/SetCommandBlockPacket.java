package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class SetCommandBlockPacket extends PacketBuilder {
    private BlockPosition position;
    private String command;
    private Mode mode;
    private boolean trackOutput;
    private boolean conditional;
    private boolean alwaysActive;

    protected SetCommandBlockPacket(BlockPosition position, String command, Mode mode,
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

    public static SetCommandBlockPacket create(BlockPosition position, String command, Mode mode,
                                               boolean trackOutput, boolean conditional, boolean alwaysActive) {
        return Mapping.get().packetManager().incoming().setCommandBlockPacket(position, command, mode, trackOutput, conditional, alwaysActive);
    }
}
