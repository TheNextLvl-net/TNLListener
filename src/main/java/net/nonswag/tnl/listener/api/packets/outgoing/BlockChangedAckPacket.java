package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BlockChangedAckPacket extends PacketBuilder {
    private int sequence;

    public static BlockChangedAckPacket create(int sequence) {
        return Mapping.get().packetManager().outgoing().blockChangedAckPacket(sequence);
    }
}
