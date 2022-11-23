package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

@Getter
@Setter
public class PlayerItemPickEvent extends PlayerEvent {
    private final int slot;

    public PlayerItemPickEvent(TNLPlayer player, int slot) {
        super(player);
        this.slot = slot;
    }
}
