package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class ChatAckPacket extends PacketBuilder {

    private LastSeenMessages.Update lastSeenMessages;

    protected ChatAckPacket(LastSeenMessages.Update lastSeenMessages) {
        this.lastSeenMessages = lastSeenMessages;
    }

    public static ChatAckPacket create(LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatAckPacket(lastSeenMessages);
    }
}
