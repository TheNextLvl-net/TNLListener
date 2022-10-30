package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerAbilitiesPacket implements IncomingPacket {
    private boolean flying;

    public PlayerAbilitiesPacket(boolean flying) {
        this.flying = flying;
    }
}
