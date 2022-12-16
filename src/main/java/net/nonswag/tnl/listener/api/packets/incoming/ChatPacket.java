package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatPacket extends PacketBuilder {
    private String message;
    private Instant timeStamp;
    private long salt;
    @Nullable
    private byte[] signature;
    private LastSeenMessages.Update lastSeenMessages;

    public static ChatPacket create(String message, Instant timeStamp, long salt, @Nullable byte[] signature, LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatPacket(message, timeStamp, salt, signature, lastSeenMessages);
    }
}
