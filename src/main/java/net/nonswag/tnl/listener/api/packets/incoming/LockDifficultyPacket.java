package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LockDifficultyPacket extends PacketBuilder {
    private boolean locked;

    public static LockDifficultyPacket create(boolean locked) {
        return Mapping.get().packetManager().incoming().lockDifficultyPacket(locked);
    }
}
