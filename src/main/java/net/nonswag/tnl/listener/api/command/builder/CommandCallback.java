package net.nonswag.tnl.listener.api.command.builder;

import com.google.common.annotations.Beta;
import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.logger.Console;
import net.nonswag.core.api.platform.PlatformPlayer;
import net.nonswag.tnl.listener.api.command.Usable;
import net.nonswag.tnl.listener.api.command.exceptions.*;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.server.Server;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Beta
@Getter
public class CommandCallback implements Usable {
    @Nonnull
    private final List<CommandCallbackInfo> callbackInfo = new ArrayList<>();
    @Nullable
    private String permission, usage;
    @Nonnull
    private Executor executor = Executor.BOTH;
    @Nullable
    private BiConsumer<CommandSource, CommandParameters> providedArguments;
    private boolean restAsString = false;

    public CommandCallback(@Nonnull String... paths) {
        for (String path : paths) addSubCommand(path);
    }

    @Nonnull
    public CommandCallback addSubCommand(@Nonnull String path) {
        if (path.isEmpty()) return this;
        callbackInfo.add(new CommandPath(callbackInfo.size(), path));
        return this;
    }

    public CommandCallback addSubCommand(@Nonnull String name, @Nonnull Value commandType, @Nonnull String... suggested) {
        int index = callbackInfo.size();
        callbackInfo.add(new CommandParameter(index, name, commandType, Arrays.asList(suggested)));
        if (commandType.equals(Value.REST_OF_INPUT)) restAsString = true;
        return this;
    }

    @Nonnull
    public CommandCallback permission(@Nonnull String permission) {
        this.permission = permission;
        return this;
    }

    @Nonnull
    public CommandCallback usage(@Nonnull String usage) {
        this.usage = usage;
        return this;
    }

    @Nonnull
    public CommandCallback executor(@Nonnull Executor executor) {
        this.executor = executor;
        return this;
    }

    @Nonnull
    public CommandCallback commandCallback(@Nonnull BiConsumer<CommandSource, CommandParameters> providedArguments) {
        this.providedArguments = providedArguments;
        return this;
    }

    @Nonnull
    private String getSuggested(@Nonnull CommandBuilder<?> command) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("§7/§b").append(command.getName()).append(" ");
        for (CommandCallbackInfo callback : callbackInfo) stringBuilder.append(callback.commandHelpPlaceholder()).append(" ");
        return stringBuilder.toString();
    }

    CommandCallbackInfo getCallbackInfo(int index) {
        if (index < 0 || index >= callbackInfo.size())
            return null;
        return callbackInfo.get(index);
    }

    @Nonnull
    protected List<String> suggest(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        List<String> suggested = new ArrayList<>();
        if (permission != null && !permission.isEmpty() && !source.hasPermission(permission)) return suggested;
        if (executor.equals(Executor.PLAYER) && !(source instanceof TNLPlayer)) return suggested;
        else if (executor.equals(Executor.CONSOLE) && !(source instanceof Console)) return suggested;
        int currentArgument = args.length - 1;
        if (callbackInfo.size() <= currentArgument) return suggested;
        if (currentArgument == -1) return List.of("");
        for (int i = 0; i < args.length - 1; i++) {
            String argument = args[i];
            if (callbackInfo.size() <= i) return suggested;
            CommandCallbackInfo info = callbackInfo.get(i);
            if (info instanceof CommandPath path && !argument.equalsIgnoreCase(path.getPath())) return suggested;
        }
        return callbackInfo.get(currentArgument).suggest(args[currentArgument]);
    }

    protected void execute(@Nonnull Invocation invocation) throws CommandException {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (canUse(source)) throw new SourceMismatchException();
        if (permission != null && !permission.isEmpty() && !source.hasPermission(permission))
            throw new InsufficientPermissionException(permission);
        if (args.length != callbackInfo.size() && !restAsString) throw new InvalidUseException(this);
        if (restAsString && args.length == 0) throw new InvalidUseException(this);
        if (this.providedArguments == null) return;
        List<Object> providedArguments = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String argument = args[i];
            CommandCallbackInfo info = callbackInfo.get(i);
            if (info instanceof CommandPath path) {
                if (!argument.equalsIgnoreCase(path.getPath())) throw new InvalidUseException(this);
            } else if (info instanceof CommandParameter commandParameter) {
                if (commandParameter.getValue().isPlayer()) {
                    if (commandParameter.getValue().equals(Value.ONLINE_PLAYER)) {
                        Player player = Bukkit.getPlayerExact(argument);
                        if (player == null) throw new PlayerNotOnlineException(argument);
                        providedArguments.add(player);
                    } else if (commandParameter.getValue().equals(Value.CACHED_PLAYER)) {
                        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(argument);
                        if (player == null) throw new UnknownPlayerException(argument);
                        providedArguments.add(player);
                    }
                } else if (commandParameter.getValue().isNumber()) {
                    try {
                        if (commandParameter.getValue().equals(Value.INTEGER)) {
                            providedArguments.add(Integer.parseInt(argument));
                        } else if (commandParameter.getValue().equals(Value.DOUBLE)) {
                            providedArguments.add(Double.parseDouble(argument));
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidUseException(this);
                    }
                } else if (commandParameter.getValue().equals(Value.BOOLEAN)) {
                    if (!argument.equalsIgnoreCase("true") && !argument.equalsIgnoreCase("false"))
                        throw new InvalidUseException(this);
                    providedArguments.add(Boolean.parseBoolean(argument));
                } else if (commandParameter.getValue().equals(Value.SERVER)) {
                    Server server = Server.wrap(argument);
                    if (server == null) throw new InvalidUseException(this);
                    providedArguments.add(server);
                } else if (commandParameter.getValue().equals(Value.STRING)) {
                    providedArguments.add(argument);
                } else if (commandParameter.getValue().equals(Value.REST_OF_INPUT)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = i; j < args.length; j++) stringBuilder.append(args[j]).append(" ");
                    providedArguments.add(stringBuilder.toString());
                    break;
                }
            }
        }
        this.providedArguments.accept(source, new CommandParameters(providedArguments));
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        if (getUsage() != null && !getUsage().isEmpty()) invocation.source().sendMessage(getUsage());
        else invocation.source().sendMessage(Messages.INVALID_COMMAND_USAGE);
    }

    @Override
    public boolean canUse(@Nonnull CommandSource source) {
        return switch (getExecutor()) {
            case PLAYER -> source instanceof PlatformPlayer;
            case CONSOLE -> source instanceof Console;
            case BOTH -> true;
        };
    }

    public enum Value {
        STRING,
        ENUM,
        REST_OF_INPUT,
        INTEGER,
        DOUBLE,
        ONLINE_PLAYER,
        CACHED_PLAYER,
        BOOLEAN,
        SERVER;

        public boolean isNumber() {
            return equals(INTEGER) || equals(DOUBLE);
        }

        public boolean isPlayer() {
            return equals(CACHED_PLAYER) || equals(ONLINE_PLAYER);
        }
    }

    public enum Executor {
        PLAYER,
        CONSOLE,
        BOTH
    }

    public static class CommandParameters {
        @Nonnull
        private final List<Object> parameters;

        private CommandParameters(@Nonnull List<Object> parameters) {
            this.parameters = parameters;
        }

        public <T> T getObject(@Nonnegative int index) {
            return (T) parameters.get(index);
        }

        public <E extends Enum<?>> E getEnum(@Nonnegative int index, @Nonnull Class<E> enumeration) {
            String input = getObject(index);
            return Arrays.stream(enumeration.getEnumConstants()).filter(constant -> constant.name().equals(input)).findAny().orElse(null);
        }

        public Class<?> getType(@Nonnegative int index) {
            return parameters.get(index).getClass();
        }

        public int size() {
            return parameters.size();
        }
    }

    @Getter
    public abstract static class CommandCallbackInfo {
        private final int index;

        protected CommandCallbackInfo(int index) {
            this.index = index;
        }

        @Nonnull
        public abstract String commandHelpPlaceholder();

        @Nonnull
        public abstract List<String> suggest(@Nonnull String argument);
    }

    @Getter
    public static class CommandPath extends CommandCallbackInfo {
        @Nonnull
        private final String path;

        private CommandPath(int index, @Nonnull String path) {
            super(index);
            this.path = path;
        }

        @Nonnull
        @Override
        public List<String> suggest(@Nonnull String argument) {
            if (getPath().contains(argument)) return List.of(getPath());
            return List.of();
        }

        @Nonnull
        @Override
        public String commandHelpPlaceholder() {
            return getPath();
        }

        @Nonnull
        @Override
        public String toString() {
            return getPath();
        }
    }

    @Getter
    public static class CommandParameter extends CommandCallbackInfo {
        @Nonnull
        private final String name;
        @Nonnull
        private final Value value;
        @Nonnull
        private final List<String> suggested;

        private CommandParameter(int index, @Nonnull String name, @Nonnull Value value, @Nonnull List<String> suggested) {
            super(index);
            this.name = name;
            this.value = value;
            this.suggested = suggested;
        }

        @Nonnull
        @Override
        public List<String> suggest(@Nonnull String argument) {
            return switch (value) {
                case INTEGER -> List.of("0");
                case DOUBLE -> List.of("0.0");
                case ONLINE_PLAYER -> Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
                case CACHED_PLAYER -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList());
                case BOOLEAN -> List.of("true", "false");
                case SERVER -> Server.servers().stream().map(Server::getName).collect(Collectors.toList());
                default -> suggested.stream().filter(s -> s.contains(argument)).collect(Collectors.toList());
            };
        }

        @Nonnull
        @Override
        public String commandHelpPlaceholder() {
            return "§8[§6%s§8]".formatted(getName());
        }

        @Override
        public String toString() {
            return "CommandParameter{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
