package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommandSuggestionPacket extends PacketBuilder {
    private int id;
    private String command;

    public static CommandSuggestionPacket create(int id, String command) {
        return Mapping.get().packetManager().incoming().commandSuggestionPacket(id, command);
    }
}
