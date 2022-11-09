package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.manager.ResourceManager;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResourcePackPacket extends PacketBuilder {
    private ResourceManager.Action action;

    public static ResourcePackPacket create(ResourceManager.Action action) {
        return Mapping.get().packetManager().incoming().resourcePackPacket(action);
    }
}
