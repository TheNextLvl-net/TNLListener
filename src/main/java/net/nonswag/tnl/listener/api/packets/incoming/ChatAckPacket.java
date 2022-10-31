package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ChatAckPacket extends PacketBuilder {

    @Nonnull
    private LastSeenMessages.Update lastSeenMessages;

    protected ChatAckPacket(@Nonnull LastSeenMessages.Update lastSeenMessages) {
        this.lastSeenMessages = lastSeenMessages;
    }

    @Nonnull
    public static ChatAckPacket create(@Nonnull LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatAckPacket(lastSeenMessages);
    }
}
