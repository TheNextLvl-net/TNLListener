package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

@Getter
public class PlayerInteractEvent extends PlayerEvent {
    private final Block clickedBlock;
    private final Direction direction;
    private final ItemStack heldItem;

    public PlayerInteractEvent(TNLPlayer player, Block clickedBlock, Direction direction, ItemStack heldItem) {
        super(player);
        this.clickedBlock = clickedBlock;
        this.direction = direction;
        this.heldItem = heldItem;
    }
}
