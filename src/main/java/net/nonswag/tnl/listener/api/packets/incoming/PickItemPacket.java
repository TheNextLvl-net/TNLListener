package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PickItemPacket extends PacketBuilder {
    private int slot;

    public static PickItemPacket create(int slot) {
        return Mapping.get().packetManager().incoming().pickItemPacket(slot);
    }
}
