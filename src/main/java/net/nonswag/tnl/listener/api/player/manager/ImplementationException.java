package net.nonswag.tnl.listener.api.player.manager;

public class ImplementationException extends RuntimeException {

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
