package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetBorderCenterPacket extends PacketBuilder {

    @Nonnull
    private VirtualBorder.Center center;

    protected SetBorderCenterPacket(@Nonnull VirtualBorder.Center center) {
        this.center = center;
    }

    @Nonnull
    public static SetBorderCenterPacket create(@Nonnull VirtualBorder.Center center) {
        return Mapping.get().packetManager().outgoing().setBorderCenterPacket(center);
    }
}
