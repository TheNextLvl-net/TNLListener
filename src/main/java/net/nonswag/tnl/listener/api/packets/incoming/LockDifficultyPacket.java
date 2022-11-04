package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class LockDifficultyPacket extends PacketBuilder {
    private boolean locked;

    protected LockDifficultyPacket(boolean locked) {
        this.locked = locked;
    }

    public static LockDifficultyPacket create(boolean locked) {
        return Mapping.get().packetManager().incoming().lockDifficultyPacket(locked);
    }
}
