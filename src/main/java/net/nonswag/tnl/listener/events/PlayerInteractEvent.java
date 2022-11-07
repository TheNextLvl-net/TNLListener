package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
public class PlayerInteractEvent extends PlayerEvent {

    @Nonnull
    private final Block clickedBlock;
    @Nonnull
    private final Direction direction;
    @Nonnull
    private final ItemStack heldItem;

    public PlayerInteractEvent(@Nonnull TNLPlayer player, @Nonnull Block clickedBlock, @Nonnull Direction direction, @Nonnull ItemStack heldItem) {
        super(player);
        this.clickedBlock = clickedBlock;
        this.direction = direction;
        this.heldItem = heldItem;
    }
}
