package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockDifficultyPacket implements IncomingPacket {
    private boolean locked;

    public LockDifficultyPacket(boolean locked) {
        this.locked = locked;
    }
}
