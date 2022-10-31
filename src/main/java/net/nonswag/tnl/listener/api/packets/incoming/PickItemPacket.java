package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PickItemPacket extends PacketBuilder {
    private int slot;

    protected PickItemPacket(int slot) {
        this.slot = slot;
    }

    @Nonnull
    public static PickItemPacket create(int slot) {
        return Mapping.get().packetManager().incoming().pickItemPacket(slot);
    }
}
