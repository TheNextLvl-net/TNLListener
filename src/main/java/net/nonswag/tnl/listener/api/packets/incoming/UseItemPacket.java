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
public abstract class UseItemPacket extends PacketBuilder {
    private Hand hand;
    private int sequence;

    public static UseItemPacket create(Hand hand, int sequence) {
        return Mapping.get().packetManager().incoming().useItemPacket(hand, sequence);
    }
}
