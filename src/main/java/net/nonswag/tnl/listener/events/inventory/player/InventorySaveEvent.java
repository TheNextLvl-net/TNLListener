package net.nonswag.tnl.listener.events.inventory.player;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public class InventorySaveEvent extends PlayerEvent {

    @Nonnull
    private final String inventoryId;

    public InventorySaveEvent(@Nonnull TNLPlayer player, @Nonnull String inventoryId) {
        super(player);
        this.inventoryId = inventoryId;
    }

    @Nonnull
    public String getInventoryId() {
        return inventoryId;
    }
}
