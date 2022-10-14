package net.nonswag.tnl.listener.api.logger;


import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public abstract class LogManager {

    @Getter
    @Nonnull
    private static final LogManager instance = Mapping.get().logManager();
    @Nonnull
    public static final Pattern LOG_4_SHELL = Pattern.compile(".*\\$\\{[^}]*}.*");

    public abstract void initialize();
}
