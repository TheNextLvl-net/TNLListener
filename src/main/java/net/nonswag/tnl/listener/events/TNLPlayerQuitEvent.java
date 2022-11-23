package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

public class TNLPlayerQuitEvent extends PlayerEvent {
    public TNLPlayerQuitEvent(TNLPlayer player) {
        super(player);
    }
}
