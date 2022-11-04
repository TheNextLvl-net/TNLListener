package net.nonswag.tnl.listener.api.command.builder.callback;

import com.google.common.annotations.Beta;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.command.CommandSource;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Beta
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface CommandSuggestionCallback<S extends CommandSource> {
    List<String> suggest(S source, String[] args);
}
