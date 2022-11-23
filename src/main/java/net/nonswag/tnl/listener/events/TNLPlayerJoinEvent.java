package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

public class TNLPlayerJoinEvent extends PlayerEvent {
    public TNLPlayerJoinEvent(TNLPlayer player) {
        super(player);
    }

    public boolean isFirstJoin() {
        return !getPlayer().hasPlayedBefore();
    }
}
