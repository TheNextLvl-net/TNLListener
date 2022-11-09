package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TeleportToEntityPacket extends PacketBuilder {
    private UUID target;

    public static TeleportToEntityPacket create(UUID target) {
        return Mapping.get().packetManager().incoming().teleportToEntityPacket(target);
    }
}
