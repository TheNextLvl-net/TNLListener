package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class RenameItemPacket extends PacketBuilder {
    private String name;

    protected RenameItemPacket(String name) {
        this.name = name;
    }

    public static RenameItemPacket create(String name) {
        return Mapping.get().packetManager().incoming().renameItemPacket(name);
    }
}
