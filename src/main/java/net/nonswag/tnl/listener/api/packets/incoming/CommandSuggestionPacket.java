package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class CommandSuggestionPacket implements IncomingPacket {
    @Nonnull
    private String partialCommand;
    private int id;

    public CommandSuggestionPacket(int id, @Nonnull String partialCommand) {
        this.id = id;
        this.partialCommand = partialCommand;
    }
}
