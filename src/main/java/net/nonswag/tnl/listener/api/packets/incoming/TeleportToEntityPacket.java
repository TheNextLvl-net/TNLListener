package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.UUID;

@Getter
@Setter
public class TeleportToEntityPacket implements IncomingPacket {
    @Nonnull
    private UUID target;

    public TeleportToEntityPacket(@Nonnull UUID target) {
        this.target = target;
    }
}
