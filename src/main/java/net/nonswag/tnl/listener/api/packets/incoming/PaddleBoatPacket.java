package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaddleBoatPacket implements IncomingPacket {
    private boolean left;
    private boolean right;

    public PaddleBoatPacket(boolean left, boolean right) {
        this.left = left;
        this.right = right;
    }
}
