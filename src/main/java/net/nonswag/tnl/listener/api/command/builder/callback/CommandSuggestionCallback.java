package net.nonswag.tnl.listener.api.command.builder.callback;

import net.nonswag.core.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

public interface CommandSuggestionCallback<S extends CommandSource> {
    @Nonnull
    List<String> suggest(@Nonnull S source, @Nonnull String[] args);
}
