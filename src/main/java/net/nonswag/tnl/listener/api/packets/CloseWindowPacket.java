package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
public abstract class CloseWindowPacket extends PacketBuilder {

    private int windowId;

    protected CloseWindowPacket(int windowId) {
        this.windowId = windowId;
    }

    @Nonnull
    public CloseWindowPacket setWindowId(int windowId) {
        this.windowId = windowId;
        return this;
    }

    @Nonnull
    public static CloseWindowPacket create(int windowId) {
        return Mapping.get().packets().closeWindowPacket(windowId);
    }

    @Nonnull
    public static CloseWindowPacket create() {
        return create(1);
    }
}
