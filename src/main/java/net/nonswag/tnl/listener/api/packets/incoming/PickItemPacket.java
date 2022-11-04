package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class PickItemPacket extends PacketBuilder {
    private int slot;

    protected PickItemPacket(int slot) {
        this.slot = slot;
    }

    public static PickItemPacket create(int slot) {
        return Mapping.get().packetManager().incoming().pickItemPacket(slot);
    }
}
