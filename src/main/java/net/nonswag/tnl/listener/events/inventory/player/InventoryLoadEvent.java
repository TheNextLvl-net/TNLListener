package net.nonswag.tnl.listener.events.inventory.player;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

@Getter
public class InventoryLoadEvent extends PlayerEvent {
    private final String inventoryId;

    public InventoryLoadEvent(TNLPlayer player, String inventoryId) {
        super(player);
        this.inventoryId = inventoryId;
    }
}
