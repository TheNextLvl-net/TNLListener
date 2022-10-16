package net.nonswag.tnl.listener.api.command.simple;

import net.nonswag.core.api.command.CommandSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ConsoleSubCommand extends SubCommand {

    protected ConsoleSubCommand(@Nonnull String name, @Nullable String permission, @Nonnull String... aliases) {
        super(name, permission, aliases);
    }

    protected ConsoleSubCommand(@Nonnull String name) {
        super(name);
    }

    @Override
    public boolean canUse(@Nonnull CommandSource source) {
        return source.isConsole();
    }
}
