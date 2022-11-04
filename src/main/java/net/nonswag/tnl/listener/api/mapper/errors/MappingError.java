package net.nonswag.tnl.listener.api.mapper.errors;

import net.nonswag.core.api.errors.TNLRuntimeException;

import javax.annotation.Nullable;

public class MappingError extends TNLRuntimeException {

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
