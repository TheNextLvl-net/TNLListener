package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.command.simple.SimpleCommand;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public class InvalidUseException extends CommandException {

    @Nullable
    private final TNLCommand command;
    @Nullable
    private final SubCommand subCommand;

    public InvalidUseException(@Nonnull TNLCommand command) {
        this.command = command;
        this.subCommand = null;
    }

    public InvalidUseException(@Nonnull SubCommand subCommand) {
        this.subCommand = subCommand;
        this.command = null;
    }

    @Override
    public void handle(@Nonnull Invocation invocation) {
        if (getCommand() != null) {
            String usage = getCommand().getUsage();
            if (getCommand() instanceof SimpleCommand command) command.usage(invocation);
            else if (!usage.isEmpty()) invocation.source().sendMessage(usage);
        } else if (getSubCommand() != null) getSubCommand().usage(invocation);
    }
}
