package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CloseWindowPacket extends PacketBuilder {

    private int windowId;

    protected CloseWindowPacket(int windowId) {
        this.windowId = windowId;
    }

    @Nonnull
    public static CloseWindowPacket create(int windowId) {
        return Mapping.get().packetManager().outgoing().closeWindowPacket(windowId);
    }

    @Nonnull
    public static CloseWindowPacket create() {
        return create(1);
    }
}
