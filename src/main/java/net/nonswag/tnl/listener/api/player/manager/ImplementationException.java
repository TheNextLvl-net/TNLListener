package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.errors.TNLRuntimeException;

import javax.annotation.Nonnull;

public class ImplementationException extends TNLRuntimeException {

    public ImplementationException() {
    }

    public ImplementationException(@Nonnull String message) {
        super(message);
    }

    public ImplementationException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public ImplementationException(@Nonnull Throwable cause) {
        super(cause);
    }
}
