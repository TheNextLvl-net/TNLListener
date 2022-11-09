package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatPacket extends PacketBuilder {
    private String message;
    private Instant timeStamp;
    private long salt;
    private byte[] signature;
    private boolean signedPreview;
    private LastSeenMessages.Update lastSeenMessages;

    public static ChatPacket create(String message, Instant timeStamp, long salt, byte[] signature, boolean signedPreview, LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatPacket(message, timeStamp, salt, signature, signedPreview, lastSeenMessages);
    }
}
