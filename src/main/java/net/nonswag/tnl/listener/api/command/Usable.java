package net.nonswag.tnl.listener.api.command;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;

import javax.annotation.Nonnull;

public interface Usable {
    void usage(@Nonnull Invocation invocation);

    boolean canUse(@Nonnull CommandSource source);
}
