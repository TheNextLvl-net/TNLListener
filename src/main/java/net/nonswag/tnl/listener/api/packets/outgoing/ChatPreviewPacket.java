package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;

@Getter
@Setter
@FieldsAreNullableByDefault
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChatPreviewPacket extends PacketBuilder {
    private int queryId;
    @Nullable
    private Component query;

    public static ChatPreviewPacket create(int queryId, @Nullable Component query) {
        return Mapping.get().packetManager().outgoing().chatPreviewPacket(queryId, query);
    }
}
