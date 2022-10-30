package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;

@Getter
@Setter
public class UseItemOnPacket implements IncomingPacket {
    @Nonnull
    private Hand hand;
    @Nonnull
    private RayTraceResult target;
    private int sequence;

    public UseItemOnPacket(@Nonnull Hand hand, @Nonnull RayTraceResult target, int sequence) {
        this.hand = hand;
        this.target = target;
        this.sequence = sequence;
    }
}
