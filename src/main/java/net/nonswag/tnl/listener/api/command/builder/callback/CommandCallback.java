package net.nonswag.tnl.listener.api.command.builder.callback;

import net.nonswag.core.api.command.CommandSource;

import javax.annotation.Nonnull;

public interface CommandCallback<S extends CommandSource> {
    void execute(S sender, @Nonnull String[] args);
}
