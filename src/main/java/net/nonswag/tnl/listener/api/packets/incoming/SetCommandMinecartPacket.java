package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SetCommandMinecartPacket implements IncomingPacket {
    @Nonnull
    private String command;
    private final int entityId;
    private boolean trackOutput;

    public SetCommandMinecartPacket(int entityId, @Nonnull String command, boolean trackOutput) {
        this.entityId = entityId;
        this.command = command;
        this.trackOutput = trackOutput;
    }
}
