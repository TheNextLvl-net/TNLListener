package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ChatPreviewPacket implements IncomingPacket {
    @Nonnull
    private String query;
    private int queryId;

    public ChatPreviewPacket(int queryId, @Nonnull String query) {
        this.queryId = queryId;
        this.query = query;
    }
}
