package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ChatPreviewPacket extends PacketBuilder {
    @Nonnull
    private String query;
    private int queryId;

    protected ChatPreviewPacket(int queryId, @Nonnull String query) {
        this.queryId = queryId;
        this.query = query;
    }

    @Nonnull
    public static ChatPreviewPacket create(int queryId, @Nonnull String query) {
        return Mapping.get().packetManager().incoming().chatPreviewPacket(queryId, query);
    }
}
