package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ChatAckPacket implements IncomingPacket {

    @Nonnull
    private LastSeenMessages.Update lastSeenMessages;

    public ChatAckPacket(@Nonnull LastSeenMessages.Update lastSeenMessages) {
        this.lastSeenMessages = lastSeenMessages;
    }
}
