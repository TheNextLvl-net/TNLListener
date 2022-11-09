package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatPreviewPacket extends PacketBuilder {
    private int queryId;
    private String query;

    public static ChatPreviewPacket create(int queryId, String query) {
        return Mapping.get().packetManager().incoming().chatPreviewPacket(queryId, query);
    }
}
