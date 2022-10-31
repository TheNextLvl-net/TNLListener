package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetCommandMinecartPacket extends PacketBuilder {
    @Nonnull
    private String command;
    private final int entityId;
    private boolean trackOutput;

    protected SetCommandMinecartPacket(int entityId, @Nonnull String command, boolean trackOutput) {
        this.entityId = entityId;
        this.command = command;
        this.trackOutput = trackOutput;
    }

    @Nonnull
    public static SetCommandMinecartPacket create(int entityId, @Nonnull String command, boolean trackOutput) {
        return Mapping.get().packetManager().incoming().setCommandMinecartPacket(entityId, command, trackOutput);
    }
}
