package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class OpenBookPacket extends PacketBuilder {

    @Nonnull
    private Hand hand;

    protected OpenBookPacket(@Nonnull Hand hand) {
        this.hand = hand;
    }

    @Nonnull
    public static OpenBookPacket create(@Nonnull Hand hand) {
        return Mapping.get().packetManager().outgoing().openBookPacket(hand);
    }
}
