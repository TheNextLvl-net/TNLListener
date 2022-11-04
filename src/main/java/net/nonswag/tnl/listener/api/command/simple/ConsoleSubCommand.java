package net.nonswag.tnl.listener.api.command.simple;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.logger.Console;

import javax.annotation.Nullable;

public abstract class ConsoleSubCommand extends SubCommand {

    protected ConsoleSubCommand(String name, @Nullable String permission, String... aliases) {
        super(name, permission, aliases);
    }

    protected ConsoleSubCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUse(CommandSource source) {
        return source instanceof Console;
    }
}
