package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CommandSuggestionPacket extends PacketBuilder {
    @Nonnull
    private String partialCommand;
    private int id;

    protected CommandSuggestionPacket(int id, @Nonnull String partialCommand) {
        this.id = id;
        this.partialCommand = partialCommand;
    }

    @Nonnull
    public static CommandSuggestionPacket create(int id, @Nonnull String partialCommand) {
        return Mapping.get().packetManager().incoming().commandSuggestionPacket(id, partialCommand);
    }
}
