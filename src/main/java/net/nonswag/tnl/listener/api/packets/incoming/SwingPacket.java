package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

@Getter
@Setter
public abstract class SwingPacket extends PacketBuilder {
    private Hand hand;

    protected SwingPacket(Hand hand) {
        this.hand = hand;
    }

    public static SwingPacket create(Hand hand) {
        return Mapping.get().packetManager().incoming().swingPacket(hand);
    }
}
