package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.time.Instant;

@Getter
@Setter
public abstract class ChatPacket extends PacketBuilder {
    private String message;
    private Instant timeStamp;
    private LastSeenMessages.Update lastSeenMessages;
    private byte[] signature;
    private boolean signedPreview;
    private long salt;

    protected ChatPacket(String message, Instant timeStamp, long salt, byte[] signature, boolean signedPreview, LastSeenMessages.Update lastSeenMessages) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.salt = salt;
        this.signature = signature;
        this.signedPreview = signedPreview;
        this.lastSeenMessages = lastSeenMessages;
    }

    public static ChatPacket create(String message, Instant timeStamp, long salt, byte[] signature, boolean signedPreview, LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatPacket(message, timeStamp, salt, signature, signedPreview, lastSeenMessages);
    }
}
