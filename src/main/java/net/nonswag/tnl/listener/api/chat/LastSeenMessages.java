package net.nonswag.tnl.listener.api.chat;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LastSeenMessages {

    private List<Entry> entries;

    public LastSeenMessages(List<LastSeenMessages.Entry> entries) {
        this.entries = entries;
    }

    public record Entry(UUID profileId, byte[] lastSignature) {
    }

    @Getter
    @Setter
    public static class Update {

        private LastSeenMessages lastSeen;
        @Nullable
        private LastSeenMessages.Entry lastReceived;

        public Update(LastSeenMessages lastSeen, @Nullable LastSeenMessages.Entry lastReceived) {
            this.lastSeen = lastSeen;
            this.lastReceived = lastReceived;
        }
    }
}
