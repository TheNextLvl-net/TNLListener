package net.nonswag.tnl.listener.api.chat;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LastSeenMessages {

    @Nonnull
    private List<Entry> entries;

    public LastSeenMessages(@Nonnull List<LastSeenMessages.Entry> entries) {
        this.entries = entries;
    }

    public record Entry(@Nonnull UUID profileId, @Nonnull byte[] lastSignature) {
    }

    @Getter
    @Setter
    public static class Update {

        @Nonnull
        private LastSeenMessages lastSeen;
        @Nullable
        private LastSeenMessages.Entry lastReceived;

        public Update(@Nonnull LastSeenMessages lastSeen, @Nullable LastSeenMessages.Entry lastReceived) {
            this.lastSeen = lastSeen;
            this.lastReceived = lastReceived;
        }
    }
}
