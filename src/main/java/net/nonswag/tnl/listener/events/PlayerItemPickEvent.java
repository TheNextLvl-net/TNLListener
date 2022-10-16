package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public class PlayerItemPickEvent extends PlayerEvent {

    private final int slot;

    public PlayerItemPickEvent(@Nonnull TNLPlayer player, int slot) {
        super(player);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
