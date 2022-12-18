package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.chat.ChatType;
import net.nonswag.tnl.listener.api.chat.Filter;
import net.nonswag.tnl.listener.api.chat.MessageSignature;
import net.nonswag.tnl.listener.api.chat.SignedMessageBody;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerChatPacket extends PacketBuilder {
    private UUID sender;
    private int index;
    @Nullable
    private MessageSignature signature;
    private SignedMessageBody body;
    @Nullable
    Component unsignedContent;
    Filter filter;
    ChatType chatType;

    public static PlayerChatPacket create(UUID sender, int index, @Nullable MessageSignature signature, SignedMessageBody body, @Nullable Component unsignedContent, Filter filter, ChatType chatType) {
        return Mapping.get().packetManager().outgoing().playerChatPacket(sender, index, signature, body, unsignedContent, filter, chatType);
    }
}
