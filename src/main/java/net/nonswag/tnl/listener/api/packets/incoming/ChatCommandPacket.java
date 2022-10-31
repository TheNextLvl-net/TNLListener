package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.time.Instant;

@Getter
@Setter
public abstract class ChatCommandPacket extends PacketBuilder {

    @Nonnull
    private String command;
    @Nonnull
    private Instant timeStamp;
    @Nonnull
    private Entry[] argumentSignatures;
    @Nonnull
    private LastSeenMessages.Update lastSeenMessages;
    private boolean signedPreview;
    private long salt;

    protected ChatCommandPacket(@Nonnull String command, @Nonnull Instant timeStamp, long salt, @Nonnull Entry[] argumentSignatures, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages) {
        this.command = command;
        this.timeStamp = timeStamp;
        this.salt = salt;
        this.argumentSignatures = argumentSignatures;
        this.signedPreview = signedPreview;
        this.lastSeenMessages = lastSeenMessages;
    }

    @Getter
    @Setter
    public static class Entry {
        @Nonnull
        private String name;
        @Nonnull
        private final byte[] signature;

        public Entry(@Nonnull String name, @Nonnull byte[] signature) {
            this.name = name;
            this.signature = signature;
        }
    }

    @Nonnull
    public static ChatCommandPacket create(@Nonnull String command, @Nonnull Instant timeStamp, long salt, @Nonnull Entry[] argumentSignatures, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatCommandPacket(command, timeStamp, salt, argumentSignatures, signedPreview, lastSeenMessages);
    }
}
