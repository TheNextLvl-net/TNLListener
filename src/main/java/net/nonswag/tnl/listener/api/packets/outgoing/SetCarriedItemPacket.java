package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetCarriedItemPacket extends PacketBuilder {

    private int slot;

    protected SetCarriedItemPacket(int slot) {
        this.slot = slot;
    }

    @Nonnull
    public static SetCarriedItemPacket create(int slot) {
        return Mapping.get().packetManager().outgoing().setCarriedItemPacket(slot);
    }
}
