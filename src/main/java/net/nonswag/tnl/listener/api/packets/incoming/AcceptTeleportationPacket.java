package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptTeleportationPacket implements IncomingPacket {

    private int id;

    public AcceptTeleportationPacket(int id) {
        this.id = id;
    }
}
