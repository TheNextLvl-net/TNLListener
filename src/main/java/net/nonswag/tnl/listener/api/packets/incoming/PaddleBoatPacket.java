package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PaddleBoatPacket extends PacketBuilder {
    private boolean left, right;

    public static PaddleBoatPacket create(boolean left, boolean right) {
        return Mapping.get().packetManager().incoming().paddleBoatPacket(left, right);
    }
}
