package net.nonswag.tnl.listener.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.nonswag.core.api.command.CommandSource;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class CommandEvent extends TNLEvent {
    private final CommandSource source;
    private final String fullCommand;

    protected CommandEvent(CommandSource source, String fullCommand) {
        this.source = source;
        this.fullCommand = fullCommand;
    }

    public String getCommand() {
        return getFullCommand().split(" ")[0];
    }
}
