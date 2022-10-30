package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.player.Hand;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SwingPacket implements IncomingPacket {
    @Nonnull
    private Hand hand;

    public SwingPacket(@Nonnull Hand hand) {
        this.hand = hand;
    }
}
