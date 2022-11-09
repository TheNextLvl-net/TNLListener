package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerInputPacket extends PacketBuilder {
    private float sideways, forward;
    private boolean jumping, sneaking;

    public static PlayerInputPacket create(float sideways, float forward, boolean jumping, boolean sneaking) {
        return Mapping.get().packetManager().incoming().playerInputPacket(sideways, forward, jumping, sneaking);
    }
}
