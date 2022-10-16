package net.nonswag.tnl.listener.api.command.exceptions;

import net.nonswag.core.api.command.Invocation;

import javax.annotation.Nonnull;

public abstract class CommandException extends RuntimeException {

    protected CommandException() {
    }

    protected CommandException(@Nonnull String message) {
        super(message);
    }

    protected CommandException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    protected CommandException(@Nonnull Throwable cause) {
        super(cause);
    }

    protected CommandException(@Nonnull String message, @Nonnull Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract void handle(@Nonnull Invocation invocation);
}
