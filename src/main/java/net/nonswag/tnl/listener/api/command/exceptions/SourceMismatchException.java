package net.nonswag.tnl.listener.api.command.exceptions;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.logger.Console;
import net.nonswag.core.api.platform.PlatformPlayer;
import net.nonswag.tnl.listener.utils.Messages;

public class SourceMismatchException extends CommandException {

    @Override
    public void handle(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source instanceof Console) source.sendMessage(Messages.PLAYER_COMMAND);
        else if (source instanceof PlatformPlayer) source.sendMessage(Messages.CONSOLE_COMMAND);
        else source.sendMessage(Messages.CANT_EXECUTE_COMMAND);
    }
}
