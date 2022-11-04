package net.nonswag.tnl.listener.api.logger;


import lombok.Getter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.regex.Pattern;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class LogManager {

    @Getter
    private static final LogManager instance = Mapping.get().logManager();
    public static final Pattern LOG_4_SHELL = Pattern.compile(".*\\$\\{[^}]*}.*");

    public abstract void initialize();
}
