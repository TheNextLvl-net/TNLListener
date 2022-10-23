package net.nonswag.tnl.listener.api.command.simple;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.command.exceptions.InsufficientPermissionException;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.exceptions.SourceMismatchException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SimpleCommand extends TNLCommand {

    @Nonnull
    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    protected SimpleCommand(@Nonnull String command) {
        super(command);
    }

    protected SimpleCommand(@Nonnull String command, @Nullable String permission) {
        super(command, permission);
    }

    protected SimpleCommand(@Nonnull String command, @Nullable String permission, @Nonnull String... aliases) {
        super(command, permission, aliases);
    }

    protected SimpleCommand(@Nonnull String command, @Nullable String permission, @Nonnull List<String> aliases) {
        super(command, permission, aliases);
    }

    @Override
    protected final void execute(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (!canUse(source)) throw new SourceMismatchException();
        if (args.length < 1) throw new InvalidUseException(this);
        SubCommand command = getByNameOrAlias(args[0]);
        if (command == null) throw new InvalidUseException(this);
        if (!command.canUse(source)) throw new SourceMismatchException();
        String permission = command.getPermission();
        if (permission == null || source.hasPermission(permission)) command.execute(invocation);
        else throw new InsufficientPermissionException(permission);
    }

    @Nonnull
    @Override
    protected final List<String> suggest(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        List<String> suggestions = new ArrayList<>();
        if (!canUse(source)) return suggestions;
        if (args.length <= 1) {
            for (SubCommand command : subCommands.values()) {
                if (command.canUse(source)) {
                    String permission = command.getPermission();
                    if (permission == null || source.hasPermission(permission)) suggestions.add(command.getName());
                }
            }
        } else {
            SubCommand command = getByNameOrAlias(args[0]);
            if (command != null && command.canUse(source)) {
                String permission = command.getPermission();
                if (permission == null || source.hasPermission(permission)) return command.suggest(invocation);
            }
        }
        return suggestions;
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        for (SubCommand command : subCommands.values()) {
            if (!command.canUse(source)) continue;
            if (command.getPermission() != null && !source.hasPermission(command.getPermission())) continue;
            command.usage(invocation);
        }
    }

    public void addSubCommand(@Nonnull SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    public void removeSubCommand(@Nonnull String name) {
        subCommands.remove(name);
    }

    @Nullable
    protected SubCommand getByNameOrAlias(@Nonnull String string) {
        SubCommand command = getByName(string);
        return command == null ? getByAliases(string) : command;
    }

    @Nullable
    protected SubCommand getByName(@Nonnull String name) {
        return subCommands.get(name.toLowerCase());
    }

    @Nullable
    protected SubCommand getByAliases(@Nonnull String alias) {
        for (SubCommand command : subCommands.values()) {
            if (command.getAliases().contains(alias.toLowerCase())) return command;
        }
        return null;
    }
}
