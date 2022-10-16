package net.nonswag.tnl.listener.api.command.exceptions;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.utils.Messages;

import javax.annotation.Nonnull;

public class SourceMismatchException extends CommandException {

    @Override
    public void handle(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        if (source.isConsole()) source.sendMessage(Messages.PLAYER_COMMAND);
        else if (source.isPlayer()) source.player().sendMessage(Messages.CONSOLE_COMMAND);
    }
}
