package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomChatCompletionsPacket extends PacketBuilder {
    private Action action;
    private List<String> entries;

    public enum Action {
        ADD, REMOVE, SET
    }

    public static CustomChatCompletionsPacket create(Action action, List<String> entries) {
        return Mapping.get().packetManager().outgoing().customChatCompletionsPacket(action, entries);
    }
}
