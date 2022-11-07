package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.manager.ResourceManager;

@Getter
@Setter
public abstract class ResourcePackPacket extends PacketBuilder {
    private ResourceManager.Action action;

    protected ResourcePackPacket(ResourceManager.Action action) {
        this.action = action;
    }

    public static ResourcePackPacket create(ResourceManager.Action action) {
        return Mapping.get().packetManager().incoming().resourcePackPacket(action);
    }
}
