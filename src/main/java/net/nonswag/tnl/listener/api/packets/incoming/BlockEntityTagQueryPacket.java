package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class BlockEntityTagQueryPacket extends PacketBuilder {

    @Nonnull
    private BlockPosition position;
    private int transactionId;

    protected BlockEntityTagQueryPacket(int transactionId, @Nonnull BlockPosition position) {
        this.transactionId = transactionId;
        this.position = position;
    }

    @Nonnull
    public static BlockEntityTagQueryPacket create(int transactionId, @Nonnull BlockPosition position) {
        return Mapping.get().packetManager().incoming().blockEntityTagQueryPacket(transactionId, position);
    }
}
