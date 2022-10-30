package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeepAlivePacket implements IncomingPacket {
    private long id;

    public KeepAlivePacket(long id) {
        this.id = id;
    }
}
