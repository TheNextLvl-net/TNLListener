package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.utils.Messages;

import javax.annotation.Nonnull;

@Getter
public class InsufficientPermissionException extends CommandException {

    @Nonnull
    private final String permission;

    public InsufficientPermissionException(@Nonnull String permission) {
        this.permission = permission;
    }

    @Override
    public void handle(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        if (source.isConsole()) {
            source.sendMessage(Messages.NO_PERMISSION, new Placeholder("permission", getPermission()));
        } else if (source.isPlayer()) {
            source.player().sendMessage(Messages.NO_PERMISSION, new Placeholder("permission", getPermission()));
        }
    }
}
