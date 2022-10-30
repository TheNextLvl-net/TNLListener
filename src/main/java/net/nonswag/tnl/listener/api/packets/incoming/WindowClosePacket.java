package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WindowClosePacket implements IncomingPacket {
    private int containerId;

    public WindowClosePacket(int containerId) {
        this.containerId = containerId;
    }
}
