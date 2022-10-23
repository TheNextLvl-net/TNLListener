package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.Usable;

import javax.annotation.Nonnull;

@Getter
public class InvalidUseException extends CommandException {

    @Nonnull
    private final Usable usable;

    public InvalidUseException(@Nonnull Usable usable) {
        this.usable = usable;
    }

    @Override
    public void handle(@Nonnull Invocation invocation) {
        getUsable().usage(invocation);
    }
}
