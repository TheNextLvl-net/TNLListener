package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.utils.Messages;

import javax.annotation.Nonnull;

@Getter
public class UnknownPlayerException extends CommandException {

    @Nonnull
    private final String player;

    public UnknownPlayerException(@Nonnull String player) {
        this.player = player;
    }

    @Override
    public void handle(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        if (source.isConsole()) {
            source.sendMessage(Message.format(Messages.UNKNOWN_PLAYER.message(), new Placeholder("player", getPlayer())));
        } else if (source.isPlayer()) {
            source.player().sendMessage(Messages.UNKNOWN_PLAYER, new Placeholder("player", getPlayer()));
        }
    }
}
