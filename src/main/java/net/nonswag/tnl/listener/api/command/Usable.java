package net.nonswag.tnl.listener.api.command;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;

public interface Usable {
    void usage(Invocation invocation);

    boolean canUse(CommandSource source);
}
