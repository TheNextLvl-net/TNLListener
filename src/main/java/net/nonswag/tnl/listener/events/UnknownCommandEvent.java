package net.nonswag.tnl.listener.events;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.event.CommandEvent;

public class UnknownCommandEvent extends CommandEvent {
    public UnknownCommandEvent(CommandSource source, String fullCommand) {
        super(source, fullCommand);
    }
}
