package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WindowButtonClickPacket implements IncomingPacket {
    private int containerId;
    private int buttonId;

    public WindowButtonClickPacket(int containerId, int buttonId) {
        this.containerId = containerId;
        this.buttonId = buttonId;
    }
}
