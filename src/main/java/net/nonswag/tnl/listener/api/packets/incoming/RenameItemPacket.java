package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RenameItemPacket extends PacketBuilder {
    private String name;

    public static RenameItemPacket create(String name) {
        return Mapping.get().packetManager().incoming().renameItemPacket(name);
    }
}
