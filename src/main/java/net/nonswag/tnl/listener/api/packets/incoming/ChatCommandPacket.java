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
public abstract class ChatCommandPacket extends PacketBuilder {
    private String command;
    private Instant timeStamp;
    private long salt;
    private Entry[] argumentSignatures;
    private LastSeenMessages.Update lastSeenMessages;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Entry {
        private String name;
        private final byte[] signature;
    }

    public static ChatCommandPacket create(String command, Instant timeStamp, long salt, Entry[] argumentSignatures, LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatCommandPacket(command, timeStamp, salt, argumentSignatures, lastSeenMessages);
    }
}
