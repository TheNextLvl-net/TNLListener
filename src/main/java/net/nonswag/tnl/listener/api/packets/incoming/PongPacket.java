package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PongPacket implements IncomingPacket {
    private int id;

    public PongPacket(int id) {
        this.id = id;
    }
}
