package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetCommandMinecartPacket extends PacketBuilder {
    private int entityId;
    private String command;
    private boolean trackOutput;

    public static SetCommandMinecartPacket create(int entityId, String command, boolean trackOutput) {
        return Mapping.get().packetManager().incoming().setCommandMinecartPacket(entityId, command, trackOutput);
    }
}
