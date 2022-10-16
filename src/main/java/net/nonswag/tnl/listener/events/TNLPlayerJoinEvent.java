package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public class TNLPlayerJoinEvent extends PlayerEvent {

    public TNLPlayerJoinEvent(@Nonnull TNLPlayer player) {
        super(player);
    }

    public boolean isFirstJoin() {
        return !getPlayer().hasPlayedBefore();
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
