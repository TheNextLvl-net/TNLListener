package net.nonswag.tnl.listener.events;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.event.CommandEvent;

import javax.annotation.Nonnull;

public class InsufficientPermissionEvent extends CommandEvent {

    @Nonnull
    private final String permission;

    public InsufficientPermissionEvent(@Nonnull CommandSource source, @Nonnull String fullCommand, @Nonnull String permission) {
        super(source, fullCommand);
        this.permission = permission;
    }

    @Nonnull
    public String getPermission() {
        return permission;
    }
}
