package net.nonswag.tnl.listener.api.command.builder.callback;

import com.google.common.annotations.Beta;
import net.nonswag.core.api.command.CommandSource;

import javax.annotation.ParametersAreNonnullByDefault;

@Beta
@ParametersAreNonnullByDefault
public interface CommandCallback<S extends CommandSource> {
    void execute(S sender, String[] args);
}
