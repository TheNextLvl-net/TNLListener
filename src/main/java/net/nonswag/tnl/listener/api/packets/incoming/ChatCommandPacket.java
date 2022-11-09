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
    private boolean signedPreview;
    private LastSeenMessages.Update lastSeenMessages;

    @Getter
    @Setter
    public static class Entry {
        private String name;
        private final byte[] signature;

        public Entry(String name, byte[] signature) {
            this.name = name;
            this.signature = signature;
        }
    }

    public static ChatCommandPacket create(String command, Instant timeStamp, long salt, Entry[] argumentSignatures, boolean signedPreview, LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatCommandPacket(command, timeStamp, salt, argumentSignatures, signedPreview, lastSeenMessages);
    }
}
