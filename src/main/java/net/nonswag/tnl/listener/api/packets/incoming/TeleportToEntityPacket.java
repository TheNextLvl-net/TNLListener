package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.UUID;

@Getter
@Setter
public abstract class TeleportToEntityPacket extends PacketBuilder {
    @Nonnull
    private UUID target;

    protected TeleportToEntityPacket(@Nonnull UUID target) {
        this.target = target;
    }

    @Nonnull
    public static TeleportToEntityPacket create(@Nonnull UUID target) {
        return Mapping.get().packetManager().incoming().teleportToEntityPacket(target);
    }
}
