package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public class TNLPlayerQuitEvent extends PlayerEvent {

    public TNLPlayerQuitEvent(@Nonnull TNLPlayer player) {
        super(player);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
