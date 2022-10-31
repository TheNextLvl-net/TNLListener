package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SwingPacket extends PacketBuilder {
    @Nonnull
    private Hand hand;

    protected SwingPacket(@Nonnull Hand hand) {
        this.hand = hand;
    }

    @Nonnull
    public static SwingPacket create(@Nonnull Hand hand) {
        return Mapping.get().packetManager().incoming().swingPacket(hand);
    }
}
