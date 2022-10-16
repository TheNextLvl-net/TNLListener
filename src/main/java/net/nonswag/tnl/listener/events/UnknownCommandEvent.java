package net.nonswag.tnl.listener.events;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.event.CommandEvent;

import javax.annotation.Nonnull;

public class UnknownCommandEvent extends CommandEvent {

    public UnknownCommandEvent(@Nonnull CommandSource source, @Nonnull String fullCommand) {
        super(source, fullCommand);
    }
}
