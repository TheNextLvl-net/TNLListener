package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetCommandBlockPacket extends PacketBuilder {
    private BlockPosition position;
    private String command;
    private Mode mode;
    private boolean trackOutput, conditional, alwaysActive;

    public enum Mode {
        SEQUENCE, AUTO, REDSTONE
    }

    public static SetCommandBlockPacket create(BlockPosition position, String command, Mode mode, boolean trackOutput, boolean conditional, boolean alwaysActive) {
        return Mapping.get().packetManager().incoming().setCommandBlockPacket(position, command, mode, trackOutput, conditional, alwaysActive);
    }
}
