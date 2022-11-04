package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.UUID;

@Getter
@Setter
public abstract class TeleportToEntityPacket extends PacketBuilder {
    private UUID target;

    protected TeleportToEntityPacket(UUID target) {
        this.target = target;
    }

    public static TeleportToEntityPacket create(UUID target) {
        return Mapping.get().packetManager().incoming().teleportToEntityPacket(target);
    }
}
