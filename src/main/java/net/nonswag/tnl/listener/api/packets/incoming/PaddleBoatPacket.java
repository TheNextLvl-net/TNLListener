package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class PaddleBoatPacket extends PacketBuilder {
    private boolean left;
    private boolean right;

    protected PaddleBoatPacket(boolean left, boolean right) {
        this.left = left;
        this.right = right;
    }

    public static PaddleBoatPacket create(boolean left, boolean right) {
        return Mapping.get().packetManager().incoming().paddleBoatPacket(left, right);
    }
}
