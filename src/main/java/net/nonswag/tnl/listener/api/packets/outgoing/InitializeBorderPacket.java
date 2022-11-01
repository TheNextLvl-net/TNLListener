package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class InitializeBorderPacket extends PacketBuilder {

    @Nonnull
    private VirtualBorder border;

    protected InitializeBorderPacket(@Nonnull VirtualBorder virtualBorder) {
        this.border = virtualBorder;
    }

    @Nonnull
    public static InitializeBorderPacket create(@Nonnull VirtualBorder virtualBorder) {
        return Mapping.get().packetManager().outgoing().initializeBorderPacket(virtualBorder);
    }
}
