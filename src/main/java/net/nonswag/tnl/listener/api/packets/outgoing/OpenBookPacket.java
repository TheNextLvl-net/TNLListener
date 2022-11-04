package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OpenBookPacket extends PacketBuilder {

    private Hand hand;

    public static OpenBookPacket create(Hand hand) {
        return Mapping.get().packetManager().outgoing().openBookPacket(hand);
    }
}
