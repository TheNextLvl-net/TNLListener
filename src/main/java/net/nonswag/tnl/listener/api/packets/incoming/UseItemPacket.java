package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.player.Hand;

import javax.annotation.Nonnull;

@Getter
@Setter
public class UseItemPacket implements IncomingPacket {
    @Nonnull
    private Hand hand;
    private int sequence;

    public UseItemPacket(@Nonnull Hand hand, int sequence) {
        this.hand = hand;
        this.sequence = sequence;
    }
}
