package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ResourcePackPacket extends PacketBuilder {
    private Action action;

    protected ResourcePackPacket(Action action) {
        this.action = action;
    }

    public enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED
    }

    public static ResourcePackPacket create(Action action) {
        return Mapping.get().packetManager().incoming().resourcePackPacket(action);
    }
}
