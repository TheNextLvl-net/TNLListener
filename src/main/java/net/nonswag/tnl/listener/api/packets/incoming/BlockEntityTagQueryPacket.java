package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;

import javax.annotation.Nonnull;

@Getter
@Setter
public class BlockEntityTagQueryPacket implements IncomingPacket {

    @Nonnull
    private BlockPosition position;
    private int transactionId;

    public BlockEntityTagQueryPacket(int transactionId, @Nonnull BlockPosition position) {
        this.transactionId = transactionId;
        this.position = position;
    }
}
