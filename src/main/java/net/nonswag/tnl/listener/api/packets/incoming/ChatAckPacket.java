package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatAckPacket extends PacketBuilder {
    private LastSeenMessages.Update lastSeenMessages;

    public static ChatAckPacket create(LastSeenMessages.Update lastSeenMessages) {
        return Mapping.get().packetManager().incoming().chatAckPacket(lastSeenMessages);
    }
}
