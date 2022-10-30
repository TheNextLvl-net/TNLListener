package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCarriedItemPacket implements IncomingPacket {
    private int slot;

    public SetCarriedItemPacket(int slot) {
        this.slot = slot;
    }
}
