package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.utils.Messages;

@Getter
public class InsufficientPermissionException extends CommandException {

    private final String permission;

    public InsufficientPermissionException(String permission) {
        this.permission = permission;
    }

    @Override
    public void handle(Invocation invocation) {
        invocation.source().sendMessage(Messages.NO_PERMISSION, new Placeholder("permission", getPermission()));
    }
}
