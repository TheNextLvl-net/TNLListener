package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatAckPacket extends PacketBuilder {
    private int offset;

    public static ChatAckPacket create(int offset) {
        return Mapping.get().packetManager().incoming().chatAckPacket(offset);
    }
}
