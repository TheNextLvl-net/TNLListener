package net.nonswag.tnl.listener.api.command;

import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.logger.Console;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.command.exceptions.CommandException;
import net.nonswag.tnl.listener.api.command.exceptions.SourceMismatchException;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class TNLCommand extends BukkitCommand implements Usable {

    @Nullable
    private final String permission;
    @Nullable
    private Plugin owner;

    protected TNLCommand(String command) {
        this(command, null);
    }

    protected TNLCommand(String command, @Nullable String permission, String... aliases) {
        this(command, permission, Arrays.asList(aliases));
    }

    protected TNLCommand(String command, @Nullable String permission, List<String> aliases) {
        super(command, "", "", aliases);
        this.permission = permission;
        setPermission(permission);
    }

    @Override
    public final boolean execute(CommandSender sender, String label, String[] args) {
        CommandSource source;
        if (sender instanceof Player p) {
            TNLPlayer player = TNLPlayer.cast(p);
            if (getPermission() != null && !player.permissionManager().hasPermission(getPermission())) {
                player.messenger().sendMessage(Messages.NO_PERMISSION, new Placeholder("permission", getPermission()));
                return true;
            } else source = player;
        } else source = Console.getInstance();
        Invocation invocation = new Invocation(source, label, args);
        if (!canUse(source)) {
            new SourceMismatchException().handle(invocation);
            return true;
        }
        try {
            execute(invocation);
        } catch (CommandException e) {
            e.handle(invocation);
        } catch (Throwable t) {
            if (source instanceof TNLPlayer player) {
                player.messenger().sendMessage(Messages.COMMAND_ERROR, new Placeholder("command", "/" + getName()));
                if (player.permissionManager().hasPermission("tnl.admin")) t.printStackTrace();
            } else {
                source.sendMessage(Messages.COMMAND_ERROR, new Placeholder("command", "/" + getName()));
                t.printStackTrace();
            }
        }
        return true;
    }

    protected abstract void execute(Invocation invocation);

    @Nonnull
    @Override
    public final List<String> tabComplete(CommandSender sender, String label, String[] args) {
        CommandSource source;
        if (sender instanceof Player) {
            TNLPlayer player = TNLPlayer.cast((Player) sender);
            if (getPermission() != null && !player.permissionManager().hasPermission(getPermission())) {
                return new ArrayList<>();
            } else source = player;
        } else source = Console.getInstance();
        if (!canUse(source)) return new ArrayList<>();
        List<String> suggestions;
        try {
            suggestions = suggest(new Invocation(source, label, args));
        } catch (Exception e) {
            suggestions = new ArrayList<>();
            if (source instanceof TNLPlayer player) {
                player.messenger().sendMessage(Messages.TAB_COMPLETE_ERROR, new Placeholder("command", "/" + getName()));
                if (player.permissionManager().hasPermission("tnl.admin")) e.printStackTrace();
            } else {
                source.sendMessage(Messages.TAB_COMPLETE_ERROR, new Placeholder("command", "/" + getName()));
                e.printStackTrace();
            }
        }
        suggestions.removeIf(suggestion -> !suggestion.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
        return suggestions;
    }

    @Nonnull
    @Override
    public final List<String> tabComplete(CommandSender sender, String alias, String[] args, @Nullable Location location) {
        return tabComplete(sender, alias, args);
    }

    protected List<String> suggest(Invocation invocation) {
        return new ArrayList<>();
    }

    @Nonnull
    @Override
    @Deprecated
    public final Command setPermissionMessage(@Nullable String message) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public TNLCommand setDescription(String description) {
        super.setDescription(Message.format(description));
        return this;
    }

    @Nonnull
    @Override
    public TNLCommand setUsage(String usage) {
        super.setUsage(Message.format(usage));
        return this;
    }

    @Nonnull
    @Override
    public TNLCommand setAliases(List<String> aliases) {
        super.setAliases(aliases);
        return this;
    }

    public TNLCommand setOwner(@Nullable Plugin owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public boolean canUse(CommandSource source) {
        return true;
    }

    @Override
    public void usage(Invocation invocation) {
        String usage = getUsage();
        if (!usage.isEmpty()) invocation.source().sendMessage(usage);
        else invocation.source().sendMessage(Messages.INVALID_COMMAND_USAGE);
    }
}
