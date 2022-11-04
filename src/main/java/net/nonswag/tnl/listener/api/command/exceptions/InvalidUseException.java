package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.Usable;

@Getter
public class InvalidUseException extends CommandException {

    private final Usable usable;

    public InvalidUseException(Usable usable) {
        this.usable = usable;
    }

    @Override
    public void handle(Invocation invocation) {
        getUsable().usage(invocation);
    }
}
