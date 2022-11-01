package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetBorderSizePacket extends PacketBuilder {

    private double size;

    protected SetBorderSizePacket(double size) {
        this.size = size;
    }

    @Nonnull
    public static SetBorderSizePacket create(double size) {
        return Mapping.get().packetManager().outgoing().setBorderSizePacket(size);
    }
}
