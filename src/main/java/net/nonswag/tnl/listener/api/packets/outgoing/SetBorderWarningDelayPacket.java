package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetBorderWarningDelayPacket extends PacketBuilder {

    private int warningDelay;

    protected SetBorderWarningDelayPacket(int warningDelay) {
        this.warningDelay = warningDelay;
    }

    @Nonnull
    public static SetBorderWarningDelayPacket create(int warningDelay) {
        return Mapping.get().packetManager().outgoing().setBorderWarningDelayPacket(warningDelay);
    }
}
