package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class CommandSuggestionPacket extends PacketBuilder {
    private String partialCommand;
    private int id;

    protected CommandSuggestionPacket(int id, String partialCommand) {
        this.id = id;
        this.partialCommand = partialCommand;
    }

    public static CommandSuggestionPacket create(int id, String partialCommand) {
        return Mapping.get().packetManager().incoming().commandSuggestionPacket(id, partialCommand);
    }
}
