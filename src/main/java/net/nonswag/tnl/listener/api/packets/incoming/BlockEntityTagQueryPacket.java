package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class BlockEntityTagQueryPacket extends PacketBuilder {

    private BlockPosition position;
    private int transactionId;

    protected BlockEntityTagQueryPacket(int transactionId, BlockPosition position) {
        this.transactionId = transactionId;
        this.position = position;
    }

    public static BlockEntityTagQueryPacket create(int transactionId, BlockPosition position) {
        return Mapping.get().packetManager().incoming().blockEntityTagQueryPacket(transactionId, position);
    }
}
