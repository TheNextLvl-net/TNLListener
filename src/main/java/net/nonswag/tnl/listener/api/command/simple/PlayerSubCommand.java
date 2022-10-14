package net.nonswag.tnl.listener.api.command.simple;

import net.nonswag.core.api.command.CommandSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class PlayerSubCommand extends SubCommand {

    protected PlayerSubCommand(@Nonnull String name, @Nullable String permission, @Nonnull String... aliases) {
        super(name, permission, aliases);
    }

    protected PlayerSubCommand(@Nonnull String name) {
        super(name);
    }

    @Override
    public boolean canUse(@Nonnull CommandSource source) {
        return source.isPlayer();
    }
}
