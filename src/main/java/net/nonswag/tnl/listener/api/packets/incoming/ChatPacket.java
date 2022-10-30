package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;

import javax.annotation.Nonnull;
import java.time.Instant;

@Getter
@Setter
public class ChatPacket implements IncomingPacket {
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

    public ChatPacket(@Nonnull String message, @Nonnull Instant timeStamp, long salt, @Nonnull byte[] signature, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.salt = salt;
        this.signature = signature;
        this.signedPreview = signedPreview;
        this.lastSeenMessages = lastSeenMessages;
    }
}
