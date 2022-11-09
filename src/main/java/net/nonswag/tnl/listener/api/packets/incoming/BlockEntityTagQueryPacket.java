package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BlockEntityTagQueryPacket extends PacketBuilder {
    private int transactionId;
    private BlockPosition position;

    public static BlockEntityTagQueryPacket create(int transactionId, BlockPosition position) {
        return Mapping.get().packetManager().incoming().blockEntityTagQueryPacket(transactionId, position);
    }
}
