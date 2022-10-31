package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class WindowButtonClickPacket extends PacketBuilder {
    private int containerId;
    private int buttonId;

    protected WindowButtonClickPacket(int containerId, int buttonId) {
        this.containerId = containerId;
        this.buttonId = buttonId;
    }

    @Nonnull
    public static WindowButtonClickPacket create(int containerId, int buttonId) {
        return Mapping.get().packetManager().incoming().windowButtonClickPacket(containerId, buttonId);
    }
}
