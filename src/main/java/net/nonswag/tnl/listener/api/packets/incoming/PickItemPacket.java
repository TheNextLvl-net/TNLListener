package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickItemPacket implements IncomingPacket {
    private int slot;

    public PickItemPacket(int slot) {
        this.slot = slot;
    }
}
