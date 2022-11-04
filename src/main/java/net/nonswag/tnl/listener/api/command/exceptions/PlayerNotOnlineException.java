package net.nonswag.tnl.listener.api.command.exceptions;

import lombok.Getter;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.utils.Messages;

@Getter
public class PlayerNotOnlineException extends CommandException {

    private final String player;

    public PlayerNotOnlineException(String player) {
        this.player = player;
    }

    @Override
    public void handle(Invocation invocation) {
        invocation.source().sendMessage(Messages.PLAYER_NOT_ONLINE, new Placeholder("player", getPlayer()));
    }
}
