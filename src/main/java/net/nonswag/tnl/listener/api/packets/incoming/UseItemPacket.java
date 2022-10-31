package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class UseItemPacket extends PacketBuilder {
    @Nonnull
    private Hand hand;
    private int sequence;

    protected UseItemPacket(@Nonnull Hand hand, int sequence) {
        this.hand = hand;
        this.sequence = sequence;
    }

    @Nonnull
    public static UseItemPacket create(@Nonnull Hand hand, int sequence) {
        return Mapping.get().packetManager().incoming().useItemPacket(hand, sequence);
    }
}
