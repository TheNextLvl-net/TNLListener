package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.time.Instant;

@Getter
@Setter
public abstract class ChatPacket extends PacketBuilder {
    @Nonnull
    private String message;
    @Nonnull
    private Instant timeStamp;
    @Nonnull
    private LastSeenMessages.Update lastSeenMessages;
    @Nonnull
    private byte[] signature;
    private boolean signedPreview;
    private long salt;

    protected ChatPacket(@Nonnull String message, @Nonnull Instant timeStamp, long salt, @Nonnull byte[] signature, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.salt = salt;
        this.signature = signature;
        this.signedPreview = signedPreview;
        this.lastSeenMessages = lastSeenMessages;
    }

    @Nonnull
    public static ChatPacket create(@Nonnull String message, @Nonnull Instant timeStamp, long salt, @Nonnull byte[] signature, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatPacket(message, timeStamp, salt, signature, signedPreview, lastSeenMessages);
    }
}
