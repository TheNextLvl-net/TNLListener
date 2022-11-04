package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ChatPreviewPacket extends PacketBuilder {
    private String query;
    private int queryId;

    protected ChatPreviewPacket(int queryId, String query) {
        this.queryId = queryId;
        this.query = query;
    }

    public static ChatPreviewPacket create(int queryId, String query) {
        return Mapping.get().packetManager().incoming().chatPreviewPacket(queryId, query);
    }
}
