package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class ChatPreviewPacket extends PacketBuilder {
    @Nullable
    private String query;
    private int queryId;

    protected ChatPreviewPacket(int queryId, @Nullable String query) {
        this.queryId = queryId;
        this.query = query;
    }

    @Nonnull
    public static ChatPreviewPacket create(int queryId, @Nullable String query) {
        return Mapping.get().packetManager().outgoing().chatPreviewPacket(queryId, query);
    }
}
