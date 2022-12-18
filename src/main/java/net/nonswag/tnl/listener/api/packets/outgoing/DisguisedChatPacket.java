package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.chat.ChatType;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor
public abstract class DisguisedChatPacket extends PacketBuilder {
    private Component message;
    private ChatType chatType;

    public static DisguisedChatPacket create(Component message, ChatType chatType) {
        return Mapping.get().packetManager().outgoing().disguisedChatPacket(message, chatType);
    }
}
