package net.nonswag.tnl.listener.api.command.builder;

import com.google.common.annotations.Beta;
import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.builder.callback.CommandSuggestionCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Beta
public class CommandBuilder<S extends CommandSource> {

    @Getter
    private final String name;
    private final Map<Integer, CommandSuggestionCallback<S>> suggestionCallbackCache = new ConcurrentHashMap<>();
    private final List<CommandCallback> commandCallbacks = new ArrayList<>();

    public CommandBuilder(String name) {
        this.name = name;
    }

    public CommandCallback addCommandCallback(String... path) {
        CommandCallback callback = new CommandCallback(path);
        commandCallbacks.add(callback);
        return callback;
    }

    public final List<String> suggest(Invocation invocation) {
        List<String> suggest = new ArrayList<>();
        for (CommandCallback callback : commandCallbacks) {
            List<String> suggested = callback.suggest(invocation);
            if (!suggested.isEmpty()) suggest.addAll(suggested);
        }
        return suggest;
    }
}
