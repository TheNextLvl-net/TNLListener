package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.errors.TNLRuntimeException;

public class ImplementationException extends TNLRuntimeException {

    public ImplementationException() {
    }

    public ImplementationException(String message) {
        super(message);
    }

    public ImplementationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImplementationException(Throwable cause) {
        super(cause);
    }
}
