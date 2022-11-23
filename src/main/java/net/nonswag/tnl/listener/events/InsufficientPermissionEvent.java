package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.event.CommandEvent;

@Getter
@Setter
public class InsufficientPermissionEvent extends CommandEvent {
    private String permission;

    public InsufficientPermissionEvent(CommandSource source, String fullCommand, String permission) {
        super(source, fullCommand);
        this.permission = permission;
    }
}
