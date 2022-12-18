package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.chat.ChatSession;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatSessionUpdatePacket extends PacketBuilder {
    private ChatSession session;

    public static ChatSessionUpdatePacket create(ChatSession session) {
        return Mapping.get().packetManager().incoming().chatSessionUpdatePacket(session);
    }
}
