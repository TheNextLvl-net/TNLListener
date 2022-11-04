package net.nonswag.tnl.listener.api.command.exceptions;

import net.nonswag.core.api.command.Invocation;

public abstract class CommandException extends RuntimeException {

    protected CommandException() {
    }

    protected CommandException(String message) {
        super(message);
    }

    protected CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CommandException(Throwable cause) {
        super(cause);
    }

    protected CommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract void handle(Invocation invocation);
}
