package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlayerInputPacket extends PacketBuilder {
    private float sideways;
    private float forward;
    private boolean jumping;
    private boolean sneaking;

    protected PlayerInputPacket(float sideways, float forward, boolean jumping, boolean sneaking) {
        this.sideways = sideways;
        this.forward = forward;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    @Nonnull
    public static PlayerInputPacket create(float sideways, float forward, boolean jumping, boolean sneaking) {
        return Mapping.get().packetManager().incoming().playerInputPacket(sideways, forward, jumping, sneaking);
    }
}
