package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SwingPacket extends PacketBuilder {
    private Hand hand;

    public static SwingPacket create(Hand hand) {
        return Mapping.get().packetManager().incoming().swingPacket(hand);
    }
}
