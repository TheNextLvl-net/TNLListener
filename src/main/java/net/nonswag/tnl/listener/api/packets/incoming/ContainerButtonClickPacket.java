package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerButtonClickPacket extends PacketBuilder {
    private int containerId, buttonId;

    public static ContainerButtonClickPacket create(int containerId, int buttonId) {
        return Mapping.get().packetManager().incoming().containerButtonClickPacket(containerId, buttonId);
    }
}
