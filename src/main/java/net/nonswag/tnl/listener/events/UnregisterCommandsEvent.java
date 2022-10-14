package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.TNLEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class UnregisterCommandsEvent extends TNLEvent {

    @Nonnull
    private final List<String> commands;

    public UnregisterCommandsEvent(@Nonnull List<String> commands) {
        this.commands = commands;
    }

    @Nonnull
    public List<String> getCommands() {
        return commands;
    }
}
