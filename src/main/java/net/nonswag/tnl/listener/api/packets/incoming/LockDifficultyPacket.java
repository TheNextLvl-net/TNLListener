package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class LockDifficultyPacket extends PacketBuilder {
    private boolean locked;

    protected LockDifficultyPacket(boolean locked) {
        this.locked = locked;
    }

    @Nonnull
    public static LockDifficultyPacket create(boolean locked) {
        return Mapping.get().packetManager().incoming().lockDifficultyPacket(locked);
    }
}
