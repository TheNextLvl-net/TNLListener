package net.nonswag.tnl.listener.api.mapper.errors;

import javax.annotation.Nullable;

public class MappingError extends RuntimeException {

    public MappingError() {
    }

    public MappingError(String message) {
        super(message);
    }

    public MappingError(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingError(Throwable cause) {
        super(cause);
    }

    public MappingError(@Nullable String message, @Nullable Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
