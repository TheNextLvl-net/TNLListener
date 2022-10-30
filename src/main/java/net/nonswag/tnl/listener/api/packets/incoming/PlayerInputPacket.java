package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerInputPacket implements IncomingPacket {
    private float sideways;
    private float forward;
    private boolean jumping;
    private boolean sneaking;

    public PlayerInputPacket(float sideways, float forward, boolean jumping, boolean sneaking) {
        this.sideways = sideways;
        this.forward = forward;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }
}
