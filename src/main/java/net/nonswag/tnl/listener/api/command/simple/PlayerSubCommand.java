package net.nonswag.tnl.listener.api.command.simple;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.platform.PlatformPlayer;

import javax.annotation.Nullable;

public abstract class PlayerSubCommand extends SubCommand {

    protected PlayerSubCommand(String name, @Nullable String permission, String... aliases) {
        super(name, permission, aliases);
    }

    protected PlayerSubCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUse(CommandSource source) {
        return source instanceof PlatformPlayer;
    }
}
