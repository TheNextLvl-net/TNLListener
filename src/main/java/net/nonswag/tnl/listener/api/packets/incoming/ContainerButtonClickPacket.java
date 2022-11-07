package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ContainerButtonClickPacket extends PacketBuilder {
    private int containerId;
    private int buttonId;

    protected ContainerButtonClickPacket(int containerId, int buttonId) {
        this.containerId = containerId;
        this.buttonId = buttonId;
    }

    public static ContainerButtonClickPacket create(int containerId, int buttonId) {
        return Mapping.get().packetManager().incoming().containerButtonClickPacket(containerId, buttonId);
    }
}
