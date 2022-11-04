package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class SetCarriedItemPacket extends PacketBuilder {
    private int slot;

    protected SetCarriedItemPacket(int slot) {
        this.slot = slot;
    }

    public static SetCarriedItemPacket create(int slot) {
        return Mapping.get().packetManager().incoming().setCarriedItemPacket(slot);
    }
}
